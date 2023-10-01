<template>
  <div>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 15]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>
</template>

<script>
export default {
  props: {
    total: {
      type: Number,
      required: true
    },
    currentPage: {
      type: Number,
      default: 1
    },
    pageSize: {
      type: Number,
      default: 10
    }
  },
  methods: {
    handleCurrentChange(val) {
      this.$emit('change-page', val); // Emitting the change to the parent
    },
    handleSizeChange(val) {
      this.$emit('change-size', val); // Emitting the size change to the parent
    }
  }
};
</script>

<style scoped>
/* 如果有任何样式，请在此处添加 */
</style>
