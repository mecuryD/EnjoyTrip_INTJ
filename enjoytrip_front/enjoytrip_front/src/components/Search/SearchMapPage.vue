<script setup>
import Map from "./SearchMap.vue";
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { getSido, getGugun, searchMap } from "@/api/search";
import SearchListItem from "../item/SearchListItem.vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const tourType = [
    { value: 0, name: "전체" },
    { value: 12, name: "관광지" },
    { value: 14, name: "문화시설" },
    { value: 15, name: "축제공연행사" },
    { value: 25, name: "여행코스" },
    { value: 28, name: "레포츠" },
    { value: 32, name: "숙박" },
    { value: 38, name: "쇼핑" },
    { value: 39, name: "음식점" },
];
const router = useRouter();
const list = ref([]);
const sido = ref([]);
const gugun = ref([]);

const selectedSido = ref("");
const selectedGugun = ref("");
const selectedTourType = ref("");
const selectedKeyword = ref("");

const searchParam = ref({
    sidoCode: "",
    gugunCode: "",
    contentTypeId: "",
    searchContent: "",
    userId: userInfo.value.userId,
})

onMounted(() => {
    getSido((data) => {
        sido.value = data.data;
    }, (error) => {
        console.log("에러 : 시도코드");
        console.log(error);
        router.push({ name: "errorpg" });
    });

})

watch(selectedSido, (newValue, oldValue) => {
    getGugun(newValue, (data) => {
        gugun.value = data.data;
    }, (error) => {
        console.log("에러 : 구군코드");
        console.log(error);
        router.push({ name: "errorpg" });
    });
});

const getSearch = () => {
    searchParam.value.sidoCode = selectedSido.value;
    searchParam.value.gugunCode = selectedGugun.value;
    searchParam.value.contentTypeId = selectedTourType.value;
    searchParam.value.searchContent = selectedKeyword.value;

    searchMap(searchParam.value, (data) => {
        console.log(data.data);
        list.value = data.data;
    }, (error) => {
        console.log("에러 : 검색");
        console.log(error);
        router.push({ name: "errorpg" });
    });
};
</script>

<template>
    <div class="first">
        <Map :list="list" class="map"></Map>
        <div class="search-space">
            <v-card class="search-input-box">
                <div class="search-cell">
                    <v-select
                        class="select-box"
                        label="시도"
                        :items="sido"
                        v-model="selectedSido"
                        item-title="sidoName"
                        item-value="sidoCode"
                    ></v-select>
                    <v-select
                        class="select-box"
                        label="구군"
                        :items="gugun"
                        v-model="selectedGugun"
                        item-title="gugunName"
                        item-value="gugunCode"
                    ></v-select>
                </div>
                <div class="search-cell">
                    <v-select
                        class="select-box"
                        label="여행지 종류"
                        v-model="selectedTourType"
                        :items="tourType"
                        item-title="name"
                        item-value="value"
                    ></v-select>
                </div>
                <div class="search-cell">
                    <v-text-field
                        class="select-box"
                        v-model="selectedKeyword"
                        append-icon="mdi-magnify"
                        @click:append="getSearch"
                        label="Search"
                        single-line
                    ></v-text-field>
                </div>
            </v-card>
            <v-card class="search-result">
                <h2>검색결과</h2><hr>
                <SearchListItem
                    v-for="searched in list"
                    :key="searched.contentId"
                    :searched="searched"
                ></SearchListItem>
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
.map{
    width: 50rem;
}
.search-result{
    margin-top: 10%;
    background-color: gainsboro;
    color: rgb(35, 35, 35);
    height: 30rem;
    overflow-y: auto;
    text-align: center;
}
.search-space{
    display: flex;
    flex-direction: column;
    width: 25rem;
    height: 45rem;
    /* padding-left: 5%; */
}
.search-input-box{
    height:14rem;
    display: flex;
    flex-direction: column;
}
.search-cell{
    display: flex;
    flex-direction: row;
    height: 32%;
}
.select-box{
    margin: 5px;
}
</style>