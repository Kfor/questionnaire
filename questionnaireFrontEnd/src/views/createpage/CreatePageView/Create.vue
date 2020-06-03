<template>
    <div class="create-page">
        <el-row class="create-page-main-row">
            <el-col :sm="8" :md="6" :lg="4" :xl="4" class="create-page-select-bar-wrapper hidden-xs-only">
                <div class="placeholder"></div>
                <SelectBar class="create-page-select-bar"
                           @addNewQuestion="addNewQuestion"
                ></SelectBar>
            </el-col>
            <el-col :sm="16" :md="16" :lg="20" :xl="20" class="create-page-question-list">
                <MainQuestionList ref="mainQuestionList"></MainQuestionList>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import SelectBar from "./SelectBar";
    import MainQuestionList from "./MainQuestionList";
    import 'element-ui/lib/theme-chalk/display.css';

    export default {
        name: "Create",
        components: {MainQuestionList, SelectBar},
        methods: {
            addNewQuestion(type) {
                this.$refs.mainQuestionList.addNewQuestion(type);
            }
        },
    }
</script>

<style scoped>
    .create-page {
        height: 100%
    }

    .create-page-main-row {
        height: 100%;
    }

    .create-page-question-list {
        height: 100%;
    }

    .create-page-select-bar {
        position: fixed;
    }

    .placeholder {
        min-width: 250px;
        min-height: 1px;
    }

</style>