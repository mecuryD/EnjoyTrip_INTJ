<script setup>
import { mapDetail } from "@/api/search";
import { useRoute } from "vue-router";
import { onMounted, ref } from "vue";

const route = useRoute();
const { contentId } = route.params;
const detailInfo = ref({});

onMounted(() => {
    mapDetail(contentId, (data) => {
        detailInfo.value = data.data;
        console.log(detailInfo.value);
    }, (error) => {
        console.log(error);
    })
})

</script>

<template>
    <div class="detail-view">
        <div class="search-info">
            <img v-bind:src="`${detailInfo.firstImage}`" class="image"/>
            <div class="detail-view">
                <h2 style="margin: 1rem;">{{ detailInfo.title }}</h2>
                {{  detailInfo.addr1 }}
                <br/>
                {{ detailInfo.addr2 }}
                <div class="search-info">
                    <v-icon icon="mdi-email" style=";"></v-icon>
                    {{ detailInfo.zipcode }}
                </div>
                <div class="search-info" v-if="detailInfo.tel">
                    <v-icon icon="mdi-phone-classic" style=";"></v-icon>
                    {{ detailInfo.tel }}
                </div>
            </div>
        </div>
            <v-textarea variant="solo-inverted" bg-color="white" rows="14"
                v-bind:model-value="detailInfo.overview" readonly>
            </v-textarea>
        <!-- {{ detailInfo }} -->
    </div>
</template>

<style scoped>
.detail-view{
    display: flex;
    flex-direction: column;
}
.search-info{
    display: flex;
    flex-direction: row;
}
.image{
    margin: 1rem;
    width: 15rem;
    height: 15rem;
}
</style>