<script setup>
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import router from "@/router";
import { ref, watch } from "vue";

const memberStore = useMemberStore();
const { userLogout } = memberStore;
const { isLogin, userInfo } = storeToRefs(memberStore);
console.log("logined : ", isLogin.value);

const menus = [
  { title: "Main", link: "/" },
  { title: "관광지 검색", link: "/search" },
  { title: "여행 계획", link: "/plan" },
  { title: "게시판", link: "/board" },
];

const usermenus = [
  { title: "회원가입", link: "/user/join" },
  { title: "로그인", link: "/user/login" },
];
const usermenusLogin = [
  { title: "마이페이지", method: () => { router.push("/user/mypage")} },
  {
    title: "로그아웃", method: () => {
      userLogout(userInfo.userId);
      router.go(0);
      console.log("isLogin : ", isLogin.value);
    }
  }
];

watch(isLogin.value, () => {
  router.go(0);
}, { deep: true })


</script>

<template>
  <v-toolbar permanent stateless value="true" class="d-inline-block elevation-0 toolbarP" flat>
    <img :src="require('@/assets/img/logos/3.png')" height="60" @click="router.push('/')" />
    <v-toolbar-items class="toolbar">
      <v-list-tile
        flat
        v-for="item in menus"
        :key="item.title"
        @click="router.push(item.link)"
        class="navbtn"
        aria-current="page"
      >
        <v-list-tile-action>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-tile-action>

        <v-list-tile-content>
          <v-list-tile-title>{{ item.title }}</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
    </v-toolbar-items>
    <v-spacer></v-spacer>
    <v-toolbar-items class="toolbar" v-if="!isLogin">
      <v-list-tile
        flat
        v-for="item in usermenus"
        :key="item.title"
        @click="router.push(item.link)"
        class="navbtn"
        aria-current="page"
      >
        <v-list-tile-action>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-tile-action>

        <v-list-tile-content>
          <v-list-tile-title>{{ item.title }}</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
    </v-toolbar-items>
    <v-toolbar-items class="toolbar" v-if="isLogin">
      <v-list-tile
        flat
        v-for="item in usermenusLogin"
        :key="item.title"
        @click="item.method"
        class="navbtn"
        aria-current="page"
      >
        <v-list-tile-action>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-tile-action>

        <v-list-tile-content>
          <v-list-tile-title>{{ item.title }}</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
    </v-toolbar-items>
  </v-toolbar>
</template>

<style scoped>
.toolbarP {
  height: 4rem;
  background-color: whitesmoke;
  padding-left: 2%;
}
.toolbar {
  align-items: center;
}
.navbtn {
  margin-right: 1rem;
  color: gray;
  text-decoration: none;
}
</style>
