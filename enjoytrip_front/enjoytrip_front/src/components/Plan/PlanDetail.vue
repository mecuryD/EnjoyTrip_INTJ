<script setup>
import PlanMap from "../item/PlanMap.vue";
import PlanDetailItem from "../item/PlanDetailItem.vue";
import { ref, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { detailPlan, deletePlan } from "@/api/plan";

const route = useRoute();
const router = useRouter();

const planInfo = ref({});
const { planId } = route.params;
const planRouteList = ref([]);

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const isWriter = ref(false);

onMounted(() => {
    detailPlan(planId, (data) => {
        planInfo.value = data.data;
        planRouteList.value = data.data.planInfos;
        if(userInfo.value.userId == planInfo.value.userId){
            isWriter.value = true;
        }
    }, (error) => {
        console.log("에러 : 정보 가져오기");
        router.push({ name:"errorpg" });
    });
    
    
});
const goPlanList = () => {
    router.push({ name:"plan-list" });
}

const goPlanModify = () => {
    router.push({ name:"plan-modify", params: { planId: planInfo.value.planId } });
}

const doPlanDelete = () => {
    deletePlan(planId, () => {
        console.log("계획 삭제 완료")
        router.push({ name:"plan-list" });
    }, (error) => {
        console.log("에러 : 계획 삭제");
        router.push({ name:"errorpg" });
    });
}
</script>

<template>
    <div class="first"
        style="justify-content: space-between; margin-right: 3rem;">
        <h2>여행 계획 열람</h2>
        <div>
            <v-btn @click="goPlanModify" style="margin-right: 1rem;" v-if="isWriter">수정</v-btn>
            <v-btn @click="doPlanDelete" style="margin-right: 1rem;" v-if="isWriter">삭제</v-btn>
            <v-btn @click="goPlanList" >목록으로</v-btn>
        </div>
    </div>
    <div class="first">
        <div class="map-space">
            <PlanMap :plans="planRouteList" class="map"></PlanMap>
            <div class="info-space">
                <v-text-field variant="solo" bg-color="white" readonly
                v-bind:model-value="planInfo.planName" class="input-box">
                </v-text-field>
                <v-text-field
                    class="btn-cal input-box" readonly
                    v-bind:model-value="planInfo.startDate"
                    prepend-inner-icon="mdi-calendar">
                </v-text-field>
                <h2 style="margin-top: 3%;">~</h2>
                <v-text-field
                    class="btn-cal input-box" readonly
                    v-bind:model-value="planInfo.endDate"
                    prepend-inner-icon="mdi-calendar">
                </v-text-field> 
            </div>
            <v-textarea
                variant="outlined" bg-color="white" counter="200" readonly
                v-bind:model-value="planInfo.planMemo" rows="5" class="input-large-box">
            </v-textarea>
        </div>
        <div class="column-space">
            <v-card class="column">
                <h2>경로</h2><hr>
                <PlanDetailItem
                    v-for="planRoute in planRouteList"
                    :key="planRoute.contentId"
                    :searched="planRoute"
                ></PlanDetailItem>
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
    width: 30rem;
    height: 40rem;
    padding-left: 5rem;
}
.column{
    margin-right: 10%;
    text-align: center;
    width: 15rem;
    background-color: gainsboro;
    color: white;
    height: 40rem;
    overflow-y: auto;
}
</style>