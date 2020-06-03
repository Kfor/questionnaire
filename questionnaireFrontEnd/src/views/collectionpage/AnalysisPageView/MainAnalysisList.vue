<template>
    <div>
        <div class="main-analysis-list">
            <el-card>
                <h1>{{questionnaire.title}}</h1>
                <div class="description-box">
                    <div>
                        <b>问卷描述：</b>{{questionnaire.description}}
                    </div>
                    <div>
                        <b>问卷状态：</b>{{translateLabel[questionnaire.status]}}
                    </div>
                    <div>
                        <b>填写人数：</b>{{questionnaire.fillCount}}
                    </div>
                    <div>
                        <b>发布时间：</b>{{questionnaire.createTime}}
                    </div>
                    <div>
                        <b>截止时间：</b>{{questionnaire.endTime}}
                    </div>
                </div>

            </el-card>
            <AnalysisCard
                    v-for="(item,index) in questionList"
                    :key="index"
                    :question-index="index+1"
                    :question-id="item.questionId"
                    :question-title="item.questionTitle"
                    :question-type="item.questionType"
            >
            </AnalysisCard>

        </div>
    </div>

</template>

<script>
    import AnalysisCard from "./AnalysisCard";

    export default {
        name: "MainAnalysisList",
        components: {AnalysisCard},
        methods: {
            fetchData() {
                this.axios.get("/api/getQuestionList", {
                    params: {
                        questionnaireId: this.$route.params.id,
                    }
                }).then((res) => {
                    this.questionList = res.data['questionList'];
                    this.$message({message: "问卷已读取", duration: 1000});
                }).catch(() => {
                    this.$message({message: "error！问卷读取失败！", duration: 1000});
                })

                this.axios.get("/api/getQuestionnaireOutline", {
                    params: {
                        questionnaireId: this.$route.params.id
                    }
                }).then((res) => {
                    this.questionnaire = res.data['questionnaire'];
                }).catch(() => {
                    this.$message({message: "error！问卷概况读取失败！", duration: 1000})
                })
            }
        },
        mounted() {
            this.fetchData();
        },
        data: function () {
            return {
                questionnaire: {
                    title: '',
                    description: '描述',
                    status: '已完成',
                    createTime: null,
                    endTime: null,
                    fillCount: 299,
                },
                questionList: [],
                translateLabel: {
                    'collecting': '收集中',
                    'editing': '编辑中',
                    'closed': '已关闭'
                },
            }
        }
    }
</script>

<style scoped>
    .main-analysis-list {
        width: 70%;
        margin: auto;
    }

    .description-box {
        text-align: left;
        padding-left: 10%;
        line-height: 30px;
        font-size: 14px;
    }
</style>