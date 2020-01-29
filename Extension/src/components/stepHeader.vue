<template>
   <h1 v-bind:class="['step-heading', {'step-heading_disabled': (!isDone && !isActive)}]"> 
       {{ title }}
   </h1>
</template>

<script>
export default {
    name: 'step-header',
    props: ['title', 'isDone', 'isActive'],
    data() {
        return {
            classes: {

            }
        }
    }
}
</script> 

<style lang="scss" scoped>
$color1: silver;
$color2: gray;

.step-heading {
    margin: 0;
    width: 100%;
    display: flex;
    align-items: center;
    padding: 3px;
    border: 1px solid $color2;
    box-sizing: border-box;
    min-height: 35px;
    font-size: 18px;
    border-top: none;
    background-color: $color1;

    &_disabled {
        color: $color2;
    }
}

</style>