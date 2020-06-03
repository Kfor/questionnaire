<template>
    <div>
        <el-row class="row-head" type="flex" justify="start">
            <el-col align="start">
                <el-button class="create-button"
                           @click="gotoCreate"
                           type="primary"
                           icon="el-icon-plus"
                           size="small"
                >创建问卷
                </el-button>
            </el-col>
            <el-col align="end" class="hidden-xs-only">
                <TypeChooseBox @changeShow="changeShow"
                ></TypeChooseBox>
            </el-col>
        </el-row>

        <el-divider></el-divider>

        <div class="questionnaire-card-ground">
            <el-row :gutter="10">
                <el-col :xs="12" :sm="6" :md="6" :lg="3" :xl="2"
                        v-for="item in questionnaireList" :key="item.questionnaireId">
                    <QuestionnaireCard class="questionnaire-card" :title="item.title" @deleteQuestionnaire="fetchData"
                                       :status="item.status" :create-time="item.createTime" :end-time="item.endTime"
                                       :id="item.questionnaireId"
                                       :description="item.description" :fill-count="item.fillCount"
                                       v-if="checkedList.indexOf(item.status)!==-1"
                    >
                    </QuestionnaireCard>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import QuestionnaireCard from "./QuestionnaireCard";
    import TypeChooseBox from "./TypeChooseBox";

    export default {
        name: "Collection",
        components: {QuestionnaireCard, TypeChooseBox},
        methods: {
            fetchData() {
                this.axios.get('/api/getQuestionnaires').then((response) => {
                    this.questionnaireList = response.data['questionnaires'];
                }).catch(() => {
                    this.$message({message: "error!问卷读取失败！", duration: 1000});
                })
            },
            gotoCreate() {
                this.axios.get('/api/createQuestionnaire').then((response) => {
                    this.$router.push('/create/' + response.data['id']);
                })
            },
            changeShow(data) {
                this.checkedList = data;
            }
        },
        mounted() {
            this.fetchData();
        },
        data: function () {
            return {
                questionnaireList: [],
                checkedList: ['collecting', 'editing', 'closed'],
            }
        }
    }
</script>

<style scoped>
    .row-head {
        margin-top: 15px;
        margin-left: 5px;
    }

</style>