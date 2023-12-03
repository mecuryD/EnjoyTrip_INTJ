<script setup>
import { defineProps } from "vue";
import { deleteComment } from "@/api/board";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();
const props = defineProps({ comment: Object })
const goDeleteComment = () => {
  deleteComment(props.comment.commentId, () => {
    console.log(props.comment.commentId)
    console.log("댓글 삭제 성공");
    router.go(0);
    // router.push({ name: "board-detail" })
  }, (error) => {
      console.log(error)
      router.push({ name: "errorpg" });
    }
  );
}
</script>

<template>
  <div class="comment">
    <v-card class="comment-text-area" width="100%">
      <v-card-item>
        <v-card-title>{{props.comment.userId}}</v-card-title>
        <v-card-subtitle>{{props.comment.commentCreatedDate}}</v-card-subtitle>
      </v-card-item>
      <v-card-text>{{props.comment.commentContent}}</v-card-text>
    </v-card>
    <div class="btn"><v-btn class="btn-delete" text="삭제" @click="goDeleteComment" ></v-btn></div>
  </div>
  </template>

<style scoped>
.comment-text-area{
    background-color: rgba(255, 251, 251, 0.854);
    margin-bottom: 1rem;
    text-align : start;
}

.comment{
  display: flex;
  flex-direction: row;
}
.btn{
  margin-left : 1%;
  margin-right : auto;
}

.btn-delete{
  background-color: rgba(255, 251, 251, 0.854);
}

</style>


