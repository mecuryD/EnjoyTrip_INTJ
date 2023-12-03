import { localAxios } from "@/util/http-commons";

const local = localAxios();   //axios instance 호출

const url = "/board"

function listArticle(success, fail) {
  local.get(`${url}`).then(success).catch(fail);
}

function detailArticle(boardId, success, fail) {
  local.get(`${url}/${boardId}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  console.log("boardjs article", article);
  local.post(`${url}`, JSON.stringify(article)).then(success).catch(fail);
}

function getModifyArticle(boardId, success, fail) {
  local.get(`${url}/modify/${boardId}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  console.log("수정할 게시글 ", JSON.stringify(article));
  local.put(`${url}/modify`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(boardId, success, fail) {
  local.delete(`${url}/${boardId}`).then(success).catch(fail);
}

function registComment(comment, success, fail) {
  console.log("boardjs comment", comment);
  local.post(`${url}/comment`, JSON.stringify(comment)).then(success).catch(fail);
}

function deleteComment(commentId, success, fail) {
  local.delete(`${url}/comment/${commentId}`).then(success).catch(fail);
}

export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
  registComment,
  deleteComment,
};
