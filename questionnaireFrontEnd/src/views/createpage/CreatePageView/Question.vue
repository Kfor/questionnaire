<template>
    <div style="position: relative;">
        <el-card class="box-card" shadow="hover" :body-style="{ padding: '0' }">

            <div class="box-is-not-selected-wrapper" v-if="!isBoxSelected" @click="clickUnSelected">
                <div class="box-is-not-selected">
                    <div class="question-title-div">
                        <div style="display: inline">{{questionIndex+1}}. {{questionTitleValue}}</div>
                        <div style="display: inline" v-if="questionNullableValue===false" class="nullable-star">*</div>
                    </div>
                    <div class="description-div">{{questionDescriptionValue}}</div>

                    <el-radio-group v-if="typeValue==='single_check'" v-model="optionsValue">
                        <el-radio v-for="(item,index) in optionsValue" :key="index">{{item}}</el-radio>
                    </el-radio-group>

                    <el-checkbox-group v-else-if="typeValue==='multi_check'" v-model="optionsValue">
                        <el-checkbox v-for="(item,index) in optionsValue" :key="index">{{item}}</el-checkbox>
                    </el-checkbox-group>

                    <el-input v-else-if="typeValue==='single_line_text'"
                              type="text" style="max-width: 80%"
                              placeholder="请输入内容（单行文本）"
                    ></el-input>

                    <el-input v-else-if="typeValue==='multi_line_text'"
                              type="textarea" style="max-width: 80%"
                              placeholder="请输入内容（多行文本）"
                    ></el-input>

                    <el-input v-else-if="typeValue==='number'" type="number"
                              style="max-width: 200px"
                              placeholder="请输入数字"
                              step="1"
                    ></el-input>

                    <el-rate v-else-if="typeValue==='grade'" :max="gradeMaxValue">
                    </el-rate>

                    <el-date-picker v-else-if="typeValue==='date'" v-model="dateValue"></el-date-picker>

                    <div v-else-if="typeValue==='text_description'" class="description-div">
                        {{textDescriptionValue}}
                    </div>

                    <div v-if="frontChoose">
                        <el-divider></el-divider>
                        <div v-for="(item,index) in frontOptionsValue" :key="index">
                            前置选项{{index+1}}：
                            <el-cascader
                                    :value="item"
                                    :options="frontOptions"
                                    :disabled="true"
                            ></el-cascader>
                        </div>
                    </div>


                </div>

                <div class="edit-icon"><i class="el-icon-edit-outline"></i></div>
            </div>


            <div class="box-is-selected" v-if="isBoxSelected">
                <div class="question-index">{{questionIndex+1}}.
                </div>
                <el-form>
                    <el-form-item label="标题：">
                        <el-input type="text" size="medium"
                                  style="max-width: 60%"
                                  v-model="questionTitleValue"
                                  placeholder="请输入标题"
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="描述：">
                        <el-input type="text" size="medium"
                                  class="description-div"
                                  v-model="questionDescriptionValue"
                                  placeholder="请输入描述"
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="类型：">
                        <el-select v-model="typeValue" placeholder="请选择" size="medium">
                            <el-option
                                    v-for="item in typeOptions"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="是否选填：">
                        <el-select placeholder="是否可以不填写" v-model="questionNullableValue">
                            <el-option :value="true" :label="'是'"></el-option>
                            <el-option :value="false" :label="'否'"></el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="是否前置：">
                        <el-tooltip placement="top"
                                    effect="light"
                                    content="是否启用级联选择（在用户选择了前置某些选项时出现）"
                        >
                            <el-select placeholder="本题在用户选择了前置某些选项时出现" v-model="frontChooseValue">
                                <el-option :value="true" :label="'是'"></el-option>
                                <el-option :value="false" :label="'否'"></el-option>
                            </el-select>
                        </el-tooltip>

                    </el-form-item>
                    <div v-if="frontChooseValue===true">
                        <el-form-item v-for="(item,index) in frontOptionsValue" :key="index"
                                      :label="'前置选项'+(index+1)+'：'">
                            <div class="block">
                                <el-cascader
                                        v-model="frontOptionsValue[index]"
                                        :options="frontOptions"
                                        :props="{ expandTrigger: 'hover' }"></el-cascader>
                            </div>
                            <el-tooltip effect="light"
                                        content="在下方添加"
                                        :open-delay="200"
                                        :hide-after="1500"
                                        placement="top">
                                <el-button size="medium" round class="add-option-button"
                                           @click="addFrontOption"
                                ><i class="el-icon-plus"></i>
                                </el-button>
                            </el-tooltip>
                            <el-tooltip effect="light"
                                        content="删除本选项"
                                        :open-delay="200"
                                        :hide-after="1500"
                                        placement="top">
                                <el-button size="medium" round class="delete-option-button"
                                           @click="deleteFrontOption(index)"
                                ><i class="el-icon-delete"></i>
                                </el-button>
                            </el-tooltip>
                        </el-form-item>
                    </div>


                    <el-divider class="divider"></el-divider>

                    <div v-if="typeValue==='single_check'||typeValue==='multi_check'">
                        <el-form-item v-for="(item,index) in optionsValue" :key="index">
                            选项{{index+1}}：
                            <el-input v-model="optionsValue[index]" style="max-width: 200px"></el-input>
                            <el-tooltip effect="light"
                                        content="在下方添加"
                                        :open-delay="200"
                                        :hide-after="1500"
                                        placement="top">
                                <el-button size="medium" round class="add-option-button"
                                           @click="addOption"
                                ><i class="el-icon-plus"></i>
                                </el-button>
                            </el-tooltip>
                            <el-tooltip effect="light"
                                        content="删除本选项"
                                        :open-delay="200"
                                        :hide-after="1500"
                                        placement="top">
                                <el-button size="medium" round class="delete-option-button"
                                           @click="deleteOption(index)"
                                ><i class="el-icon-delete"></i>
                                </el-button>
                            </el-tooltip>
                        </el-form-item>
                    </div>

                    <div v-if="typeValue==='number'">
                        <el-form-item label="数字类型：">
                            <el-select v-model="numberTypeValue" placeholder="请选择数字类型" size="medium">
                                <el-option
                                        v-for="item in numberTypeOptions"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>

                        <el-form-item label="默认数值：">
                            <el-input-number v-model="defaultNumberValue"></el-input-number>
                        </el-form-item>
                    </div>

                    <div v-if="typeValue==='grade'">
                        <el-form-item>
                            最大分数
                            <el-input-number v-model="gradeMaxValue"></el-input-number>
                        </el-form-item>
                    </div>

                    <div v-if="typeValue==='text_description'">
                        <el-form-item label="内容：">
                            <el-input v-model="textDescriptionValue"
                                      type="textarea"
                                      style="max-width: 400px"
                                      size="medium"
                            ></el-input>
                        </el-form-item>
                    </div>

                    <el-form-item>
                        <el-button type="primary" @click="saveOneQuestion">保存</el-button>
                        <el-button @click="resetQuestion">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>


            <el-button class="delete-button" type="danger"
                       icon="el-icon-delete" circle
                       @click="clickDelete"
            ></el-button>


        </el-card>
    </div>
</template>

<script>
    export default {
        name: "Question",
        props: {
            isBoxSelected: Boolean,
            questionIndex: Number,
            questionTitle: String,
            questionNullable: Boolean,
            questionType: String,
            questionDescription: String,
            questionOptions: Array,
            frontChoose: Boolean,
            frontOptions: Array,
            frontOptionsInitValue: Array,
            numberType: String,
            defaultNumber: Number,
            gradeMax: Number,
            date: Date,
            textDescription: String,
        },
        methods: {
            clickUnSelected() {
                this.$emit('clickUnSelected');
            },
            saveOneQuestion() {
                this.$emit('saveOneQuestion', this.questionData);
                this.$emit('clickSelected');
            },
            resetQuestion() {
                this.$emit('resetQuestion');
                this.questionTitleValue = "请输入标题";
                this.questionNullableValue = false;
                this.questionDescriptionValue = "请输入描述";
                this.typeValue = 'not_selected';
                this.optionsValue = [];
                this.frontOptionsValue = [[]];
                this.frontChooseValue = false;
                this.numberTypeValue = 'integer';
                this.defaultNumberValue = 0;
                this.gradeMaxValue = 5;
                this.dateValue = new Date();
                this.textDescriptionValue = '';
            },
            clickDelete() {
                this.$emit('clickDelete');
            },
            addOption() {
                this.optionsValue.push('');
            },
            deleteOption(index) {
                this.optionsValue.splice(index, 1);
            },
            addFrontOption() {
                this.frontOptionsValue.push([]);
            },
            deleteFrontOption(index) {
                this.frontOptionsValue.splice(index, 1);
            },
        },

        data: function () {
            return {
                typeOptions: [
                    {
                        value: 'not_selected',
                        label: '请输入'
                    }, {
                        value: 'single_check',
                        label: '单选'
                    }, {
                        value: 'multi_check',
                        label: '多选'
                    }, {
                        value: 'single_line_text',
                        label: '单行文本'
                    }, {
                        value: 'multi_line_text',
                        label: '多行文本'
                    }, {
                        value: 'number',
                        label: '数字'
                    }, {
                        value: 'grade',
                        label: '评分'
                    }, {
                        value: 'date',
                        label: '日期'
                    }, {
                        value: 'text_description',
                        label: '文本描述'
                    }],
                numberTypeOptions: [{
                    value: 'integer',
                    label: '整数'
                }, {
                    value: 'fraction',
                    label: '小数'
                }],
                numberTypeValue: this.numberType,
                defaultNumberValue: this.defaultNumber,
                typeValue: this.questionType,
                optionsValue: this.questionOptions,
                questionDescriptionValue: this.questionDescription,
                questionTitleValue: this.questionTitle,
                gradeMaxValue: this.gradeMax,
                dateValue: this.date,
                textDescriptionValue: this.textDescription,
                frontChooseValue: this.frontChoose,
                frontOptionsValue: this.frontOptionsInitValue,
                questionNullableValue: this.questionNullable,
            }
        },
        computed: {
            questionData: function () {
                const questionData = {
                    questionIndex: this.questionIndex,
                    questionOptions: this.optionsValue,
                    questionDescription: this.questionDescriptionValue,
                    questionTitle: this.questionTitleValue,
                    questionType: this.typeValue,
                    frontChoose: this.frontChooseValue,
                    frontOptions: this.frontOptionsValue,
                    questionNullable: this.questionNullableValue,
                    numberType: this.numberTypeValue,
                    defaultNumber: this.defaultNumberValue,
                    gradeMax: this.gradeMaxValue,
                    date: this.dateValue,
                    textDescription: this.textDescriptionValue,
                };
                return questionData;

            },

        }
    }
</script>

<style scoped>
    .box-is-not-selected {
        position: relative;
        text-align: left;
        margin-left: 20%;
        line-height: 40px;
    }

    .nullable-star {
        color: red;
    }

    .delete-button {
        position: absolute;
        right: 10%;
        top: 20px;
    }

    .question-index {
        margin-bottom: 20px;
    }

    .box-card {
        transition: all ease 300ms;
    }

    .delete-button {
        position: absolute;
    }

    .box-is-selected {
        text-align: left;
        margin-top: 30px;
        margin-bottom: 30px;
        margin-left: 20%;
    }

    .box-is-not-selected-wrapper {
        padding-top: 20px;
        padding-bottom: 20px;
        cursor: pointer;
    }

    .box-is-not-selected-wrapper:hover .box-is-not-selected {
        filter: blur(8px);
    }

    .edit-icon {
        display: none;
    }

    .box-is-not-selected-wrapper:hover .edit-icon {
        display: inline;
        height: 100%;
        width: 100%;
        background-color: rgba(179, 229, 252, 0.3);
        position: absolute;
        top: 0;
        left: 0;
    }

    .el-icon-edit-outline {
        position: absolute;
        top: calc(50% - 30px);
        left: calc(50% - 30px);
        font-size: 60px;
        color: rgba(128, 0, 128, 0.6);
    }

    .divider {
        max-width: 80%;
    }

    .add-option-button {
        margin-left: 10px;
    }

    .description-div {
        max-width: 60%;
    }
</style>