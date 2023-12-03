<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { registArticle } from "@/api/board";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const router = useRouter();
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const article = ref({
  boardContent: "",
  boardSubject: "",
  boardType: 1,
  boardView: 0,
  userId: userInfo.value.userId
});

const doWrite = () => {
  registArticle(article.value, () => {
    console.log("글 등록 성공");
    router.push({ name: "board-list" })
  }, (error) => {
      console.log(error)
      router.push({ name: "errorpg" });
    }
  );
};

const cancel = (() => {
  router.push({ name: "board-list" });
})

</script>

<template>
  <section class="view">
    <div class="alltbl">
      <v-card class="tbl-container">
        <v-text-field variant="outlined" bg-color="white"
          placeholder="제목" v-model="article.boardSubject">
        </v-text-field>
        <v-textarea
        class="input-box" variant="outlined" bg-color="white" counter="200"
        placeholder="글 내용을 입력하세요" rows="15" v-model="article.boardContent">
        </v-textarea>
        <div class="btn-cell">
          <div style="width: 80%;"></div>
          <div class="writebtn"><v-btn text="등록" @click="doWrite" ></v-btn></div>
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