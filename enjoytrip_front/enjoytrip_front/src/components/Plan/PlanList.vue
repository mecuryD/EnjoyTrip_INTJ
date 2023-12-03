<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import { listPlan } from "@/api/plan";

const router = useRouter();
const search = ref('');
const page = ref(1);
const itemsPerPage = ref(5);
const plans = ref([]);
const pageCount = computed(() => {
  return Math.ceil(plans.value.length / itemsPerPage.value);
});

const headers = [
  { title: "no", align: "center", key: "planId" },
  { title: "계획명", align: "center", sortable: "false", key: "planName" },
  { title: "작성자", align: "center", sortable: "false", key: "userId" },
  { title: "여행 시작일", align: "center", key: "startDate" },
  { title: "여행 종료일", align: "center", key: "endDate" },
];

onMounted(() => {
  console.log("plans");
  console.log(plans.value);
  getPlanList();
});

// Methods
const getPlanList = () => {
  console.log("서버에서 여행 계획 목록을 얻어옵니다 !!");
  // API 호출
  listPlan(
    ({ data }) => {
      console.log("data : {}", data);
      plans.value = data;
    },
    (error) => {
      console.log(error);
      router.push({ name: "errorpg" });
    }
  );
}

const goWritePage = () => {
  router.push({ name: "plan-write" });
};

const goPlanDetail = (event, { item }) => {
  router.push({ name: 'plan-detail', params: { planId: item.planId }});
};
</script>

  <template>
    <section class="view">
      <div class="alltbl">
        <v-text-field
          class="search"
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
      ></v-text-field>

        <v-card class="tbl-container">
          <v-data-table
            v-model:page="page"
            :headers="headers"
            :items="plans"
            :search="search"
            class="elevation-1 tbl"
            :items-per-page="itemsPerPage"
            fixed-header
            hide-default-footer
            @click:row="goPlanDetail"
          >
            <template v-slot:items="plans">
              <td>{{ plans.item.planId }}</td>
              <td class="text-xs-right">{{ plans.item.userId }}</td>
              <td class="text-xs-right">{{ plans.item.planName }}</td>
              <td class="text-xs-right">{{ plans.item.startDate }}</td>
              <td class="text-xs-right">{{ plans.item.endDate }}</td>
            </template>
            <template #bottom v-if="!showFooter"></template>
          </v-data-table>
        </v-card>
        <div class="writebtn"><v-btn text="계획 등록" @click="goWritePage"></v-btn></div>
        <div class="text-xs-center pt-2 page">
          <v-pagination v-model="page" :length="pageCount"> </v-pagination>
        </div>
      </div>
    </section>
  </template>

<style scoped>
.alltbl {
  width: 90%;
  margin-left: auto;
  margin-right: auto;
  margin-top: 10%;
  display: flex;
  flex-direction: column;
  height: 100%;
}
.tbl-container {
  display: flex;
}
.tbl {
  display: flex;
  flex-grow: 1;
  width: 100%;
}
.writebtn {
  margin-left: auto;
  margin-top: 2%;
}
.search {
  width: 40%;
  color: gray;
  margin-left: auto;
}
::v-deep .v-data-table-header__content {
  text-align: center;
  margin-left: auto;
  font-weight: 600;
}
</style>
