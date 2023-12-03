<script setup>
import { defineProps, ref, onMounted } from "vue";

const props = defineProps({ searched: Object })
// console.log(props.searched);

const goSearchDetail = () => {
    window.open("/search-detail/"+props.searched.contentId, "", "width=500, height=600");
}
const isBar = ref(false);

onMounted(() => {
    if(props.searched.contentId == 0){
        isBar.value = true;
    }
})

</script>

<template>
    <v-card class="plan-list-item">
        <div v-if="!isBar" class="plan-list-item">
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
            </div>
        </div>
        <div v-if="isBar" class="plan-list-item">
            <v-textarea
                variant="underlined" rows="3"
                v-bind:model-value="props.searched.contentMemo">
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
    margin-left: 10%;
    margin-top: 0.3rem;
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