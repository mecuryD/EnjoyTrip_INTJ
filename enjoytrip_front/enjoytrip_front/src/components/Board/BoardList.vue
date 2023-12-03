<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/board";

const router = useRouter();
const search = ref('');
const page = ref(1);
const itemsPerPage = ref(5);
const articles = ref([]);
const pageCount = computed(() => {
  return Math.ceil(articles.value.length / itemsPerPage.value);
});

const headers = [
  { title: "no", align: "center", key: "boardId" },
  { title: "제목", align: "center", sortable: "false", key: "boardSubject" },
  { title: "작성자", align: "center", sortable: "false", key: "userId" },
  { title: "댓글 수", align: "center", key: "commentCount" },
  { title: "작성일시", align: "center", key: "boardCreateDate" },
  { title: "조회수", align: "center", key: "boardView" },
];

onMounted(() => {
  console.log("articles")
  console.log(articles.value)
  getArticleList();
})

const getArticleList = () => {
  console.log("서버에서 글목록 얻어오자!!!``````");
  // API 호출
  listArticle(({ data }) => {
    console.log(data.board);
    articles.value = data.board;
  },
    (error) => {
      console.log(error)
      router.push({ name: "errorpg" });
    }
  );
};

const goBoardWrite = () => {
  router.push({ name: "board-write" });
};

const goBoardDetail = (event, { item }) => {
  console.log(item);
  router.push({ name: 'board-detail', params: {boardId: item.boardId }});
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
          :items="articles"
          :search="search"
          class="elevation-1 tbl"
          :items-per-page="itemsPerPage"
          fixed-header
          hide-default-footer
          @click:row="goBoardDetail"
        >
          <template v-slot:items="props">
            <td>{{ props.item.boardId }}</td>
            <td>
              {{ props.item.boardSubject }}
            </td>
            <td class="text-xs-right">{{ props.item.userId }}</td>
            <td class="text-xs-right">{{ props.item.commentCount }}</td>
            <td class="text-xs-right">{{ props.item.boardCreateDate }}</td>
            <td class="text-xs-right">{{ props.item.boardView }}</td>
          </template>
          <template #bottom v-if="!showFooter"></template>
        </v-data-table>
        
      </v-card>
      <div class="writebtn"><v-btn text="글쓰기" @click="goBoardWrite" ></v-btn></div>
      <div class="text-xs-center pt-2 page">
        <v-pagination v-model="page" :length="pageCount">
        </v-pagination>
      </div>
    </div>
  </section>
</template>

<style scoped>

.alltbl{
  width: 90%;
  margin-left: auto;
  margin-right: auto;
  margin-top: 10%;
  display: flex;
  flex-direction: column;
  height:100%;
}
.tbl-container{
  display: flex;
}
.tbl{
  display: flex;
  flex-grow: 1;
  width: 100%;
}
.writebtn{
  margin-left: auto;
  margin-top: 2%;
}
.search{
  width: 40%;
  color: gray;
  margin-left: auto;
}
::v-deep .v-data-table-header__content{
  text-align: center;
  margin-left: auto;
  font-weight: 600;
}
</style>