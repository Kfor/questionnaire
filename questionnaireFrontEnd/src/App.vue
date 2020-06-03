<template>
    <div id="app">
        <div v-if="this.$route.name==='fillin'">
            <router-view></router-view>
        </div>
        <div v-else style="height: 100%">
            <el-backtop></el-backtop>
            <el-container>
                <el-header>
                    <Header></Header>
                </el-header>
                <el-main>
                    <router-view></router-view>
                </el-main>
                <el-footer>
                    <Footer></Footer>
                </el-footer>
            </el-container>
        </div>
    </div>
</template>

<style lang="scss">
    html, body {
        height: 98%;
    }

    #app {
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        height: 100%;
    }

    .el-header {
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        position: fixed;
        top: 0;
        width: 100%;
        z-index: 5;
        -webkit-user-select: none;
    }

    .el-main {
        margin-top: 50px;
        overflow: auto;
        background-color: rgba(128, 128, 128, 0.05);
    }

    .el-footer {
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    }

    .el-container {
        height: 100%;
    }

</style>
<script>
    import Header from "./components/Header";
    import Footer from "./components/Footer";

    export default {
        components: {Footer, Header}
    }
</script>