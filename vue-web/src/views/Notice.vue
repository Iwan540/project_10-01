<template>
  <div>
    <h4 style="color: rgba(0,70,127);">Notice</h4>

    <div>
      <el-date-picker
          v-model="filterDate"
          type="date"
          placeholder="Select a date"
          style="width: 300px; margin: 10px 5px;"
          format="yyyy-MM-dd"
      ></el-date-picker>

      <el-button
          size="medium"
          type="primary"
          style="margin-right: 2px;"
          @click="filterByDate()"
      >Filter</el-button>

      <el-button
          size="medium"
          type="warning"
          @click="resetFilter()"
      >Reset</el-button>
    </div>

    <ul>
      <li v-for="item in notices" :key="item.nid" style="margin: 20px 20px; border: 1px solid #409EFF; padding: 20px 20px; border-radius: 20px; list-style: none;">
        <el-descriptions title="Notice" column="2" >
          <el-descriptions-item label="Date">{{ item.date }}</el-descriptions-item>
          <el-descriptions-item label="ReleaserID">{{ item.auid }}</el-descriptions-item>
          <el-descriptions-item label="Content">{{ item.content }}</el-descriptions-item>
        </el-descriptions>
      </li>
    </ul>
    <PaginationTest
        :current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        @change-page="handleCurrentChange"
        @change-size="handleSizeChange">
    </PaginationTest>
  </div>

</template>

<script>
import request from '@/utils/request'
import PaginationTest from '@/views/PaginationTest.vue';
export default {
  name: 'Notice',
  components: {
    PaginationTest
  },
  data() {
    return {
      notices: [],
      filterDate: null,
      total: 0,
      currentPage: 1,
      pageSize: 10,
    }
  },
  methods: {
    load() {
      request.get("/notice/newest", {
        params: {
          cur: this.currentPage,
          size: this.pageSize,
        }
      }).then(res => {
        this.notices = res.data.records;

        this.total = res.data.total;
      }).catch(error => {
        console.error("Error fetching notices:", error);
      });
    },
    filterByDate() {
      if (this.filterDate) {
        const dd = String(this.filterDate.getDate()).padStart(2, '0');
        const mm = String(this.filterDate.getMonth() + 1).padStart(2, '0'); // January is 0!
        const yyyy = this.filterDate.getFullYear();

        const formattedDate = yyyy + '-' + mm + '-' + dd;

        request.get("/notice/bydate", {
          params: {
            date: formattedDate,
            cur: this.currentPage,
            size: this.pageSize,
          }
        }).then(res => {
          this.notices = res.data.records;
          this.total = res.data.total;
        })
      } else {
        this.$message.warning("Please select a date to filter first!")
      }
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
    resetFilter() {
      this.filterDate = null
      this.load()
    }
  },
  created() {
    this.load()
  }
}
</script>
