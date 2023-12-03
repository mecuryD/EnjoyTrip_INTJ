<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getModifyArticle, modifyArticle } from "@/api/board";

const route = useRoute();
const router = useRouter();

const { boardId } = route.params;
const article = ref({});

onMounted(() => {
  getModifyArticle(boardId, (data) => {
    console.log("가져온 데이터", data.data);
    article.value = data.data;
  }, (error) => {
      console.log(error)
      router.push({ name: "errorpg" });
    }
  );
})

const doModify = () => {
  modifyArticle(article.value, () => {
    console.log("글 수정 성공");
    router.push({ name: "board-list" });
  }, (error) => {
      console.log(error)
      router.push({ name: "errorpg" });
    }
  );
};

const cancel = (() => {
  router.push({ name: 'board-detail', params: {boardId: article.value.boardId} });
})

</script>

<template>
  <section class="view">
    <div class="alltbl">
      <v-card class="tbl-container">
        <v-text-field variant="outlined" bg-color="white" title="제목"
          v-model="article.boardSubject">
        </v-text-field>
        <v-textarea
        class="input-box" variant="outlined" bg-color="white" counter="200"
          v-model="article.boardContent" rows="15">
        </v-textarea>
        <div class="btn-cell">
          <div style="width: 80%;"></div>
          <div class="writebtn"><v-btn text="수정" @click="doModify" ></v-btn></div>
          <div class="writebtn"><v-btn text="취소" @click="cancel" ></v-btn></div>
        </div>
      </v-card>
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
  padding: 3%;
  background-color: whitesmoke;
}
.btn-cell{
  display: flex;
  flex-direction: row;
}
.writebtn{
  margin-left: auto;
}

</style>