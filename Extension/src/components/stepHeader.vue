<template>
    <h1 v-bind:class="classes">{{ title }}</h1>
</template>

<script>
export default {
    name: 'step-header',
    props: ['title', 'isDone', 'isActive'],
    data() {
        return {
            
        }
    },
    computed: {
        classes: function() {
            return {
                'step-heading': true,
                'step': true,
                'step-heading_disabled': (!this.isActive && !this.isDone)
            }
        }
    }

}
</script> 

<style lang="scss" scoped>
$color1: #f5f5f5;
$color2: #8a8f93;
$color3: #6658f3;
$color4: white;

.step-heading {
    margin: 0;
    display: flex;
    align-items: center;
    border: 1px solid $color2;
    font-size: 18px;
    border-top: none;
    background-color: $color1;

    &_disabled {
        color: $color2;
    }
}

</style>