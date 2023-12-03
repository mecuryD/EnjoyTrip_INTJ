<script setup>
import PlanMap from "../item/PlanMap.vue";
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { VueDraggableNext } from "vue-draggable-next";
import { writePlan } from "@/api/plan";
import { getHeartList } from "@/api/search";
import PlanListItem from "../item/PlanListItem.vue";

const router = useRouter();
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const planInfo = ref({
    planId: 0,
    planInfos: [],
    planMemo: "",
    planName: "",
    startDate: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
    endDate: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
    userId: userInfo.value.userId
});

const startDate2 = ref(null);
watch(startDate2, val => {
    let date = startDate2.value;
    planInfo.value.startDate = (new Date(date - (date).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)
})
const endDate2 = ref(null);
watch(endDate2, val => {
    let date = endDate2.value;
    planInfo.value.endDate = (new Date(date - (date).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)
})

const planRouteList = ref([]);
const heartList = ref([]);

onMounted(() => {
    console.log("heartList");
    getHearts();
});

const getHearts = () => {
    console.log("서버에서 찜 목록 얻어오자!!!");
    // API 호출
    getHeartList('kimssafy',({data})=>{
        console.log(data);
        heartList.value = data;
    },(error) => {
      console.log(error)
      router.push({ name: "errorpg" });
    }
   );
}

const goPlanList = () => {
    router.push({ name:"plan-list" });
}

const doPlanWrite = () => {
    planInfo.value.planInfos = planRouteList.value;
    writePlan(planInfo.value, () => {
        console.log("계획 등록 완료!")
        router.push({ name: "plan-list" });
    }, (error) => {
        console.log(error);
    })
}

const addBar = () => {
    let obj = {
        contentId: 0,
        contentMemo: "",
    }
    planRouteList.value.push(obj);
}

</script>

<template>
    <div class="first"
        style="justify-content: space-between; margin-right: 3rem;">
        <h2>여행 계획 세우기</h2>
        <div>
            <v-btn class="btn-list" @click="doPlanWrite" style="margin-right: 1rem;">등록</v-btn>
            <v-btn class="btn-list" @click="goPlanList">목록으로</v-btn>
        </div>
    </div>
    <div class="first">
        <div class="map-space">
            <PlanMap :plans="planRouteList" class="map"></PlanMap>
            <div class="info-space">
                <v-text-field v-model="planInfo.planName"
                    variant="solo" bg-color="white"
                    label="계획명" class="input-box">
                </v-text-field>
                
                <v-dialog width="500">
                    <!-- 출발일 버튼 -->
                    <template v-slot:activator="{ props }">
                        <v-text-field
                            class="btn-cal input-box"
                            label="출발일"
                            v-model="planInfo.startDate"
                            prepend-inner-icon="mdi-calendar"
                            v-bind="props">
                        </v-text-field>
                    </template>
                    <!-- 누르면 나오는 달력 dialog -->
                    <template v-slot:default="{isActive}">
                        <v-card class="" title="출발일">
                        <v-date-picker
                            class="datepicker"
                            title=""
                            scrollable="true"
                            v-model="startDate2"
                            >
                        </v-date-picker>
                        <v-btn text="선택" @click="isActive.value = false"
                        ></v-btn>
                        </v-card>
                    </template>
                </v-dialog>
                
                <h2 style="margin-top: 3%;">~</h2>

                <v-dialog width="500">
                    <template v-slot:activator="{ props }">
                        <v-text-field
                            class="btn-cal input-box"
                            label="도착일"
                            v-model="planInfo.endDate"
                            prepend-inner-icon="mdi-calendar"
                            v-bind="props">
                        </v-text-field>
                    </template>
                    <template v-slot:default="{isActive}">
                        <v-card class="" title="도착일">
                        <v-date-picker
                            class="datepicker"
                            title=""
                            scrollable="true"
                            v-model="endDate2"
                            >
                        </v-date-picker>
                        <v-btn text="선택" @click="isActive.value = false"
                        ></v-btn>
                        </v-card>
                    </template>
                </v-dialog>
            </div>
            <v-textarea v-model="planInfo.planMemo"
                variant="outlined" bg-color="white" counter="200"
                label="글 내용" rows="7" class="input-large-box">
            </v-textarea>
        </div>
        <div class="column-space">
            <v-card class="column">
                <h2>경로</h2><hr>
                <v-card class="option"
                    variant="flat" @click="addBar">
                    addbar
                </v-card>  
                <VueDraggableNext
                    :list="planRouteList"
                    group="people">
                    <PlanListItem
                        v-for="planRoute in planRouteList"
                        :key="planRoute.contentId"
                        :searched="planRoute"
                    ></PlanListItem>
                </VueDraggableNext> 
            </v-card>
            <v-card class="column flex">
                <h2>찜</h2><hr>
                <VueDraggableNext
                    :list="heartList"
                    :group="{ name: 'people', pull: 'clone', put: false }"
                    :sort="true"
                    @change="log"
                    :move="checkMove">
                    <PlanListItem
                        v-for="heartItem in heartList"
                        :key="heartItem.contentId"
                        :searched="heartItem"
                    ></PlanListItem>
                </VueDraggableNext>
            </v-card>
        </div>
    </div>
</template>

<style scoped>
.first{
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    margin: 3%;
}
.map-space{
    display: flex;
    flex-direction: column;
    width: 40rem;
    height: 40rem;
    padding-left: 3%;
    padding-right: 3%;
    
}
.map{
    width: 40rem;
    height: 20rem;
}
.info-space{
    margin-top: 1rem;
    width: 40rem;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}
.input-box{
    margin-right: 0.3rem;
    margin-left: 0.3rem;
    width: 5rem;
    height: 2rem;
}
.input-large-box{
    margin-top: 1rem;
    width:40rem;
}
.column-space{
    display: flex;
    flex-direction: row;
    width: 40rem;
    height: 40rem;
    /* margin-left: -10%; */
    padding-left: 5%;
}
.column{
    margin-right: 10%;
    text-align: center;
    width: 20rem;
    background-color: gainsboro;
    color: white;
    height: 40rem;
    overflow-y: auto;
}
.btn-cal{
  border: 1px;
}
.datepicker{
  margin-left: auto;
  margin-right: auto;
}

.btn-list{
    background-color: rgb(42, 103, 72);
    color:white;
}
</style>