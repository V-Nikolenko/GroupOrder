<template>
    <div class="step4">
        <step-header
                v-bind:title="step.title"
                v-bind:isDone="step.isDone"
                v-bind:isActive="step.isActive"
        ></step-header>

        <div v-if="!step.isActive" class="step4__active-block">
            <div class="step4__container">
                <ul>
                    <li v-for="member in members">{{member.name}} + "  ыаыааыыа  " + {{member.fullPrice}}</li>
                </ul>
            </div>
        </div>
        <div v-else-if="step.isDone">

        </div>
    </div>
</template>

<script>
    import stepHeader from "./stepHeader";

    export default {
        name: 'step4',
        props: ['step'],
        components: {
            stepHeader
        },
        data() {
            return {
            }
        },
        computed: {
        },
        methods: {
        }
    }
</script>

<style lang="scss" scoped>
    $color1: silver;
    $color2: #4b0082;
    $color3: white;

    .step4 {
        display: flex;
        flex-direction: column;

        &__list {
            padding: 0px;
            margin: 0;
            list-style-type: none;
        }

        &__container {
            display: flex;
            flex-direction: column;
            align-items: center;
            flex: 1;
            justify-content: space-around
        }

        &__active-block {
            display: flex;
            flex-direction: column;
            border: 1px solid silver;
            border-top: none;
            padding: 3px;
            min-height: 250px;
        }

        &__delimeter {

            &::after,
            &::before {
                content: '';
                display: inline-block;
                border-top: 2px solid silver;
                width: 120px;
                position: relative;
                top: -2px;
                transform: translate(0%, -50%);
            }

            &::after {
                transform: translateX(15px)
            }

            &::before {
                transform: translateX(-15px)
            }
        }

        &__btn {
            height: 50px;
            color: $color2;
            border: 1px solid $color2;
            cursor: pointer;
            border-radius: 10px;
            background-color: $color3;
            transition-duration: 0.1s;

            &:hover {
                color: $color3;
                background-color: $color2;
            }
        }
        &__btn,
        &__input {
            box-sizing: border-box;
            width: 200px;
            padding: 5px;

        }

        &__input {
            min-height: 35px;
            border-radius: 5px;
            border: 1px solid $color1;

            &:focus {
                outline: none;
                box-shadow: 0 0 1px 2px $color1;
            }
        }

    }


</style>