<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle, registComment } from "@/api/board";
import CommentItem from "../item/CommentItem.vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const route = useRoute();
const router = useRouter();

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const isWriter = ref(false);

const { boardId } = route.params;
const article = ref({});
const comments = ref([]);
const newComment = ref({
  "boardId": boardId,
  "commentContent": "",
  "userId": userInfo.value.userId
});

onMounted(() => {
  detailArticle(boardId, (data) => {
    console.log("가져온 데이터", data.data.boardInfo);
    console.log("가져온 데이터", data.data.commentList);
    article.value = data.data.boardInfo;
    comments.value = data.data.commentList;
    if(userInfo.value.userId == article.value.userId){
      isWriter.value = true;
    }
  }, (error) => {
      console.log(error)
      router.push({ name: "errorpg" });
    }
  );
})

const goBoardModify = () => {
  router.push({ name: 'board-modify', params: {boardId: article.value.boardId} })
};

const goBoardList = (() => {
  router.push({ name: "board-list" });
})

const goBoardDelete = (() => {
  deleteArticle(boardId, () => {
    console.log("게시글 삭제");
    router.push({ name: "board-list" });
  }, (error) => {
      console.log(error)
      router.push({ name: "errorpg" });
    }
  );
})

const goCommentWrite = (()=>{
  registComment(newComment.value, () => {
    console.log("댓글 등록 성공");
    router.go(0);
    // router.push({ name: "board-detail" })
  }, (error) => {
      console.log(error)
      router.push({ name: "errorpg" });
    }
  );
})

</script>

<template>
  <section class="view">
    <div class="alltbl">
      <h1 class="detail-title">글 상세</h1>
      <v-card class="tbl-container">
        <div class="info-box">
          <v-text-field variant="solo" bg-color="white" readonly class="info-cell"
            v-bind:model-value="article.userId" label="작성자">
          </v-text-field>
          <v-text-field variant="solo" bg-color="white" readonly class="info-cell"
            v-bind:model-value="article.boardCreateDate" label="작성일시">
          </v-text-field>
          <v-text-field variant="solo" bg-color="white" readonly
            v-bind:model-value="article.boardView" label="조회수">
          </v-text-field>
        </div>
        <v-text-field variant="solo" bg-color="white" readonly
          v-bind:model-value="article.boardSubject" label="제목">
        </v-text-field>
        <v-textarea
          class="input-box" variant="solo" bg-color="white" counter="200"
          v-bind:model-value="article.boardContent" rows="15" readonly>
        </v-textarea>
        <div class="btn-cell">
          <div style="width: 65%;"></div>
          <div class="writebtn" v-if="isWriter"><v-btn text="수정" @click="goBoardModify" ></v-btn></div>
          <div class="writebtn" v-if="isWriter"><v-btn text="삭제" @click="goBoardDelete" ></v-btn></div>
          <div class="writebtn"><v-btn text="목록으로" @click="goBoardList" ></v-btn></div>
        </div>
      </v-card>
      <div class="tbl-container">  
        <CommentItem v-for="comment in comments" v-bind:key="comment" :comment="comment"></CommentItem>
      </div>
        <v-card class="tbl-container">
          <v-textarea solo name="input-7-4" label="댓글을 작성하세요" v-model="newComment.commentContent"></v-textarea>
          <div class="btn-cell comment-input-area">
            <div style="width: 65%;"></div>
            <div class="writebtn"><v-btn text="등록" @click="goCommentWrite"></v-btn></div>
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
  margin-top: 5%;
  display: flex;
  flex-direction: column;
  height:100%;
}
.tbl-container{
  padding: 3%;
  margin-bottom: 3%;
  background-color: whitesmoke;
}
.info-box{
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
.info-cell{
  margin-right: 1rem;
}
.btn-cell{
  display: flex;
  flex-direction: row;
  justify-content : flex-end;
}
.writebtn{
  margin-left: auto;
}
.detail-title{
  margin-bottom : 5%;
}

</style>