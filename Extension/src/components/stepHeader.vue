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
@import '../styles/colors.scss';

.step-heading {
    display: flex;
    align-items: center;
    margin: 0;
    display: flex;
    align-items: center;
    background-color: $color1;
    font-size: 18px;

    &_disabled {
        color: $color3;
    }
}

</style>