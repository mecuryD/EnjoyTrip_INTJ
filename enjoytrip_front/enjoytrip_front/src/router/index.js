import { createRouter, createWebHistory } from "vue-router";
import MainPageView from "@/views/MainPageView.vue";
import UserPageView from "@/views/UserPageView.vue";
import BoardPageView from "@/views/BoardPageView.vue";
import SearchPageView from "@/views/SearchPageView.vue";
import PlanPageView from "@/views/PlanPageView.vue";
import ErrorPageView from "@/components/error/ErrorPageView.vue";

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: MainPageView,
    },
    {
      path: "/user",
      name: "user",
      component: UserPageView,
      redirect: { name: "user-login" },
      children: [
        {
          path: "login",
          name: "user-login",
          component: () => import("@/components/User/LoginPage.vue"),
        },
        {
          path: "join",
          name: "user-join",
          component: () => import("@/components/User/JoinPage.vue"),
        },
        {
          path: "mypage",
          name: "user-mypage",
          component: () => import("@/components/User/MyPage.vue"),
        },
      ],
    },
    {
      path: "/board",
      name: "board",
      redirect: { name: "board-list" },
      component: BoardPageView,
      children: [
        {
          path: "list",
          name: "board-list",
          component: () => import("@/components/Board/BoardList.vue"),
        },
        {
          path: "write",
          name: "board-write",
          component: () => import("@/components/Board/BoardWrite.vue"),
        },
        {
          path: "detail/:boardId",
          name: "board-detail",
          component: () => import("@/components/Board/BoardDetail.vue"),
        },
        {
          path: "modify/:boardId",
          name: "board-modify",
          component: () => import("@/components/Board/BoardModify.vue"),
        },
      ],
    },
    {
      path: "/search",
      name: "search",
      component: SearchPageView,
      redirect: { name: "search-map" },
      children: [
        {
          path: "search",
          name: "search-map",
          component: () => import("@/components/Search/SearchMapPage.vue"),
        }
      ]
    },{
      path: '/search-detail/:contentId',
      name: 'search-detail',
      component: () => import("@/components/Search/SearchDetail.vue")
    },{
      path: '/plan',
      name: 'plan',
      component: PlanPageView,
      redirect: { name: "plan-list" },
      children: [
        {
          path: "list",
          name: "plan-list",
          component: () => import("@/components/Plan/PlanList.vue"),
        },{
          path: "write",
          name: "plan-write",
          component: () => import("@/components/Plan/PlanWrite.vue"),
        },{
          path: "detail/:planId",
          name: "plan-detail",
          component: () => import("@/components/Plan/PlanDetail.vue"),
        },{
          path: "modify/:planId",
          name: "plan-modify",
          component: () => import("@/components/Plan/PlanModify.vue"),
        }
      ]
    },{
      path: '/error',
      name: 'errorpg',
      component: ErrorPageView
    }
  ]
})

export default router;
