<template>
    <div>
        <el-form
                v-if="registerChoose===false"
                :rules="rules"
                ref="loginForm"
                v-loading="loading"
                element-loading-text="正在登录..."
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)"
                :model="loginForm"
                class="loginContainer">
            <h3 class="loginTitle">系统登录</h3>
            <el-form-item prop="username">
                <el-input size="normal" type="text" v-model="loginForm.username" auto-complete="off"
                          placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input size="normal" type="password" v-model="loginForm.password" auto-complete="off"
                          placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-link type="primary" @click="registerChoose = true" class="link">
                注册账户
            </el-link>

            <el-button size="normal" type="primary" style="width: 100%;" @click="submitLogin">登录</el-button>
        </el-form>
        <el-form
                v-if="registerChoose===true"
                :rules="rules"
                ref="registerForm"
                v-loading="loading"
                element-loading-text="正在注册..."
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)"
                :model="registerForm"
                class="loginContainer">
            <h3 class="loginTitle">注册账户</h3>
            <el-form-item prop="username">
                <el-input size="normal" type="text" v-model="registerForm.username" auto-complete="off"
                          placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input size="normal" type="password" v-model="registerForm.password" auto-complete="off"
                          placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-link type="primary" @click="registerChoose = false" class="link">
                登录账户
            </el-link>

            <el-button size="normal" type="primary" style="width: 100%;" @click="submitRegister">注册</el-button>
        </el-form>
    </div>
</template>

<script>

    export default {
        name: "Login",
        data() {
            return {
                loading: false,
                vcUrl: '/verifyCode?time=' + new Date(),
                loginForm: {
                    username: 'user',
                    password: '123',
                },
                registerForm: {
                    username: '',
                    password: '',
                },
                rules: {
                    username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'}],
                },
                registerChoose: false,
            }
        },
        methods: {

            submitLogin() {
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        this.axios.post('/api/login?username=' + this.loginForm.username + '&password=' + this.loginForm.password).then((res) => {
                            this.loading = false;
                            if(res.data==='wrong'){
                                this.$message.error({message:'账户名或密码输入错误！',duration:1000});
                            }
                            else if(res.data==='success'){
                                this.$message.success({message:'登录成功！',duration:1000});
                                this.$router.replace('/collection');
                                this.$store.commit('login',this.loginForm.username);
                            }
                        })
                    } else {
                        return false;
                    }
                });
            },
            submitRegister() {
                this.$refs.registerForm.validate((valid) => {
                    if (valid) {
                        this.axios.post('/api/register?username=' + this.registerForm.username + '&password=' + this.registerForm.password).then((res) => {
                            this.loading = false;
                            if(res.data==='usernameAlready'){
                                this.$message.error({message:'用户名已注册！',duration:1000});
                            }else if(res.data==='success'){
                                this.$message.success({message:'注册成功！',duration:1000});
                                this.registerChoose = false;
                            }
                        })
                    } else {
                        return false;
                    }
                });
            }
        }
    }
</script>

<style>
    .loginContainer {
        border-radius: 15px;
        background-clip: padding-box;
        margin: 100px auto;
        width: 250px;
        padding: 15px 35px 15px 35px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }

    .loginTitle {
        margin: 15px auto 20px auto;
        text-align: center;
        color: #505458;
    }

    .loginRemember {
        text-align: left;
        margin: 0px 0px 15px 0px;
    }

    .el-form-item__content {
        display: flex;
        align-items: center;
    }

    .link {
        margin-bottom: 15px;
    }
</style>
