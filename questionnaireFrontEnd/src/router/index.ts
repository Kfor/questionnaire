import Vue from 'vue'
import VueRouter, {RouteConfig} from 'vue-router'
import Collection from "@/views/collectionpage/MainPageView/Collection.vue"
import Create from "@/views/createpage/CreatePageView/Create.vue"
import Help from "@/views/helppage/Help.vue"
import Analysis from "@/views/collectionpage/AnalysisPageView/Analysis.vue"
import FillIn from "@/views/fillinpage/FillInPageView/FillIn.vue"
import Login from "@/views/Login.vue"

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [

    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        name: "login",
        component: Login
    },
    {
        path: '/collection',
        name: "collection",
        component: Collection,
    },
    {
        path: '/create/:id',
        name: "create",
        component: Create,
    },
    {
        path: '/help',
        name: "help",
        component: Help,
    },
    {
        path: '/analysis/:id',
        name: "analysis",
        component: Analysis,
    },
    {
        path: '/fillin/:id',
        name: "fillin",
        component: FillIn,
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
