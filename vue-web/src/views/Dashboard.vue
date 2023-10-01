<template>
    <div>
        <!-- 搜索框控件 -->
			<!-- 筛选框 -->
			<el-input  size="medium" placeholder="Input Course Name" style="width: 300px; margin: 10px 5px;"  suffix-icon="el-icon-search" v-model="courseName"></el-input>
			<el-button size="medium" type="primary" @click="load()">Filter</el-button>
			<el-button size="medium" type="warning" @click="reset">Reset</el-button>
        <!-- 表格控件 -->
        <el-table :data="tableData">
            <el-table-column prop="cname" label="Course" width="120" sortable>
            </el-table-column>
            <el-table-column prop="term" label="Term" width="120" sortable>
            </el-table-column>
            <el-table-column prop="minGrade" label="Available marker hours" sortable  align="center"  >
            </el-table-column>
            <el-table-column prop="capacity" label="Estimated number of students"  align="center" sortable>
            </el-table-column>
            <el-table-column prop="details" label="Details" width="110">
                <template slot-scope="scope">
                    <el-link @click="showDetails(scope.row)">Details<i class="el-icon-view el-icon--right"></i></el-link>
                </template>
            </el-table-column>
            <el-table-column prop="addtocart" label="Add To Cart" >
                <template slot-scope="scope">
                    <el-button type="warning" @click="addCart(scope.row)">Add To Cart</el-button>
                </template>
            </el-table-column>

        </el-table>

<!--         分页控件
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
            :page-sizes="[5, 10, 15]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>

      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 15]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>-->

      <PaginationTest :current-page="currentPage" :page-size="pageSize" :total="total" @change-page="handleCurrentChange" @change-size="handleSizeChange"></PaginationTest>


      <!-- 隐藏的表单 -->
        <el-dialog title="Course Details" :visible.sync="dialogFormVisible" width="700px">




          <el-form :model="form" style="display: flex; flex-wrap: wrap; justify-content: space-between;">


            <!-- 第一列 -->
            <div style="flex: 1; padding-right: 10px;">
              <el-form-item label="1.Current enrolled students">
                <el-input v-model="form.selected" disabled></el-input>
              </el-form-item>
              <el-form-item label="3.Course Coordinator Name">
                <el-input v-model="form.coursecoordinatorname" disabled></el-input>
              </el-form-item>
              <el-form-item label="5.Course Director Name">
                <el-input v-model="form.coursedirectorname" disabled></el-input>
              </el-form-item>
              <el-form-item label="7.Available marker hours?">
                <el-input v-model="form.minGrade" disabled></el-input>
              </el-form-item>


            </div>

            <!-- 第二列 -->
            <div style="flex: 1; padding-left: 10px;">
              <el-form-item label="2.Estimated number of students">
                <el-input v-model="form.capacity" disabled></el-input>
              </el-form-item>
              <el-form-item label="4.Course Coordinator Email">
                <el-input v-model="form.coursecoordinatoremail" disabled></el-input>
              </el-form-item>
              <el-form-item label="6.Course Director Email">
                <el-input v-model="form.coursedirectoremail" disabled></el-input>
              </el-form-item>
              <el-form-item label="8.Needs marker?">
                <el-input v-model="form.marker" disabled></el-input>
              </el-form-item>


            </div>

            <!-- 9.Number and type of assessments (full row) -->
            <div style="flex-basis: 100%; padding: 0px 0;">
              <!-- 9 和 9.1 并列 -->
              <div style="display: flex; padding: 10px 0;">
                <!-- 9.Number and type of assessments -->
                <div style="flex: 0.50; padding-right: 10px;">
                  <el-form-item label="9.Number and type of assessments">
                    <el-input v-model="form.details" disabled></el-input>
                  </el-form-item>
                </div>

                <!-- 9.1 (新加的字段) -->
                <div style="flex: 0.50; padding-left: 10px;">
                  <el-form-item label="10.Minimum Grade Requirement">
                    <el-input v-model="form.requirement" disabled></el-input>
                  </el-form-item>
                </div>
              </div>




            </div>


            <!-- 11 and 121 (side by side) -->
            <div style="flex: 0.75; padding-right: 10px;">
              <el-form-item label="11.Convert marker to tutor hours OR Convert tutor to marker hours">
                <el-input v-model="form.courseCoordinator" disabled></el-input>
              </el-form-item>
            </div>
            <div style="flex: 0.25; padding-left: 10px;">
              <el-form-item label="12. A. Convert hours">
                <el-input v-model="form.hour" disabled></el-input>
              </el-form-item>
            </div>

            <!-- 12. Marker’s responsibility for the course (full row) -->
            <div style="flex-basis: 100%; padding: 10px 0;">
              <el-form-item label="13. Marker’s responsibility for the course">
                <el-input v-model="form.courseDirector" disabled></el-input>
              </el-form-item>
            </div>
          </el-form>






          <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">exit</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>

import request from '../utils/request.js'
import PaginationTest from '@/views/PaginationTest.vue';

export default {
    name: "Dashboard",
    components: {
      PaginationTest
    },
    data() {
        return {
            user: {},
            currentPage: 1,
            pageSize: 10,
            courseName: '',
            tableData: [],
            total: 0,
            dialogFormVisible: false,
            formLabelWidth: 50,
            form: {
            },
            cart: {}
        }
    },
    methods: {
        load() {
            request.get("/course/page", {
                params: {
                    cur: this.currentPage,
                    size: this.pageSize,
                    cname: this.courseName,
                    auid: this.user.auid
                }
            }).then(res => {
                console.log(res)
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },
        handleCurrentChange(val) {
            console.log(val)
            this.currentPage = val
            this.load()
        },
        handleSizeChange(val) {
            this.pageSize = val
            this.load()
        },
        showDetails(row) {
            this.dialogFormVisible = true
            console.log(row)
            this.form = row
        },
        addCart(row) {
            this.cart.auid = this.user.auid
            this.cart.cid = row.cid
            this.cart.favors = 10
            this.cart.applied = false
            request.put("/cart/addcart", this.cart).then(res=> {
                if(res.code == 0) {
                    this.$message.success("success add!")
                }
                this.load()
            })
        },
        reset() {
            this.courseName = ""
            this.load()
        }
    },
    created() {
        this.user = JSON.parse(sessionStorage.getItem('user'))
        this.load()
    },

    setup() {

    },
}
</script>
