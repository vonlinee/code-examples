<template>
    <div id="good-list" style="border: 1px solid red;">
        商品列表
    </div>
</template>

<script>
export default {
    name: "goodlist",
    el: "#good-list",
    data: function () {
        return {
            goods: [
                {
                    name: "手机",
                    price: 2999
                },
                {
                    name: "笔记本",
                    price: 8999
                }
            ]
        }
    }
}
</script>

<style>

</style>