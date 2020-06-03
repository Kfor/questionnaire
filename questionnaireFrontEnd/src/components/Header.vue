<template>

    <el-row type="flex" justify="center" align="middle" class="header-row">
        <el-col :span="21">
            <el-menu mode="horizontal" :default-active="$route.path" router>
                <el-menu-item class="el-menu-item" index="/collection">我的问卷</el-menu-item>
                <el-menu-item class="el-menu-item" index="/create" @click="gotoCreate">创建问卷</el-menu-item>
                <el-menu-item class="el-menu-item" index="/help">帮助中心</el-menu-item>
            </el-menu>
        </el-col>
        <el-col :span="3">
            <el-avatar v-if="!this.$store.state.login" @click.native="gotoLogin" style="cursor: pointer">登录</el-avatar>
            <el-dropdown class="user-profile" v-if="this.$store.state.login">
                <el-avatar><b>{{this.$store.state.username.substr(0,1)}}</b></el-avatar>
                <el-dropdown-menu slot="dropdown" >
                    <el-dropdown-item class="share-copy-link" icon="el-icon-share" @click="copyShare"
                                      :data-clipboard-text="this.servername"
                                      data-clipboard-action="copy"
                    >分享</el-dropdown-item>
                    <el-dropdown-item icon="el-icon-close" @click.native="logout"> 退出登录</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </el-col>

    </el-row>

</template>

<script>
    import Clipboard from "clipboard";

    export default {
        name: "Header",

        methods: {
            gotoCreate() {
                this.axios.get('/api/createQuestionnaire').then((response) => {
                    const success = this.$router.push('/create/'+response.data['id']);
                })
            },
            copyShare() {
                const clipboard = new Clipboard('.share-copy-link')
                clipboard.on('success', e => {
                    console.log('复制成功')
                    this.$message({message: "复制成功", duration: 1000})
                    // 释放内存
                    clipboard.destroy()
                })
                clipboard.on('error', e => {
                    // 不支持复制
                    console.log('该浏览器不支持自动复制')
                    // 释放内存
                    clipboard.destroy()
                })
            },
            logout() {
                this.axios.post("/api/logout").then(()=>{
                    this.$store.commit('logout');
                    this.$router.replace('/login')
                })
            },
            gotoLogin() {
                this.$router.replace('/login')
            }
        }

    }
</script>

<style scoped>
    .user-profile {
        justify-content: center;
        align-items: center;
    }
    .header-row{

    }
</style>