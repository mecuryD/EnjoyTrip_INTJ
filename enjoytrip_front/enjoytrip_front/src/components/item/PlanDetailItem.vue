<script setup>
import { defineProps, onMounted, ref } from "vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { isHeart, doHeart, mapDetail } from "@/api/search";

const props = defineProps({ searched: Object })
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const isisHeart = ref(false);
const heartDto = ref({
    contentId: props.searched.contentId,
    userId: userInfo.value.userId
})

const content = ref({});
const isBar = ref(false);

onMounted(() => {
    
    if((props.searched.contentMemo == null) || (props.searched.contentMemo == "")){
        mapDetail(props.searched.contentId, (data) => {
            content.value = data.data;
        }, (error) => {
            console.log(error);
        });

        isHeart(heartDto.value, (data) => {
            if((data.data != null)&&(data.data != "")){
                isisHeart.value = true;
            }
        }, (error) => {
            console.log(error);
        });
    }else{
        isBar.value = true;
    }
});

const goSearchDetail = () => {
    window.open("/search-detail/"+content.value.contentId, "", "width=500, height=600");
}

const dodoHeart = () => {
    console.log("heart clicked!");
    doHeart(heartDto.value, () => {
        if(isisHeart.value == true){
            isisHeart.value = false;
        }else{
            isisHeart.value = true;
        }
    }, (error) => {
        console.log(error);
    })
}
</script>

<template>
    <v-card class="plan-list-item">
        <div v-if="!isBar" class="plan-list-item">
            <div>
                <img v-bind:src="`${content.firstImage}`" class="image">
            </div>
            <div class="text">
                <h5>{{ content.title }}</h5><br/>
            </div>
            <div class="icon-item">
                <v-icon
                    icon="mdi-information-outline"
                    @click="goSearchDetail">
                </v-icon>
                <v-icon
                    icon="mdi-heart-outline"
                    @click="dodoHeart" v-show="!isisHeart">
                </v-icon>
                <v-icon
                    icon="mdi-heart"
                    @click="dodoHeart" v-show="isisHeart">
                </v-icon>
            </div>
        </div>
        <div v-if="isBar" class="plan-list-item">
            <v-textarea
                variant="underlined" readonly
                rows="3" v-bind:model-value="props.searched.contentMemo">
            </v-textarea>
        </div>
    </v-card>
</template>

<style scoped>
.plan-list-item{
    width: 12rem;
    height: 15%;
    margin-left: 5%;
    margin-right: 5%;
    margin-top: 5%;
    display: flex;
    flex-direction: row;
}
.image{
    height: 4.5rem;
    width: 4.5rem;
}
.text{
    margin-top: 5%;
    margin-left: 5%;
    margin-right: 5%;
    text-align: start;
}
.icon-item{
    position: absolute;
    bottom: 3%;
    right: 3%;
}
</style>