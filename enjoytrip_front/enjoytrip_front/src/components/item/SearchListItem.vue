<script setup>
import { defineProps, ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { isHeart, doHeart } from "@/api/search";

const props = defineProps({ searched: Object });
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const isisHeart = ref(false);
const heartDto = ref({
    contentId: props.searched.contentId,
    userId: userInfo.value.userId
})

onMounted(() => {
    isHeart(heartDto.value, (data) => {
        console.log(data.data);
        if((data.data != null)&&(data.data != "")){
            isisHeart.value = true;
        }
    }, (error) => {
        console.log(error);
    })
})

const goSearchDetail = () => {
    window.open("/search-detail/"+props.searched.contentId, "", "width=500, height=600");
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
    <v-card class="search-list-item">
        <div>
            <img v-bind:src="`${props.searched.firstImage}`" class="image">
        </div>
        <div class="text">
            <h5>{{ props.searched.title }}</h5><br/>
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
    </v-card>
</template>

<style scoped>
.search-list-item{
    width: 16rem;
    height: 17%;
    margin-left: auto;
    margin-right: auto;
    margin-top: 5%;
    display: flex;
    flex-direction: row;
}
.image{
    margin-left: 10%;
    margin-top: 0.3rem;
    height: 4.5rem;
    width: 4.5rem;
}
.text{
    margin-top: 5%;
    margin-left: 5%;
}
.icon-item{
    position: absolute;
    bottom: 3%;
    right: 3%;
}
</style>