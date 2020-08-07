class StepService {
    constructor(steps) {
        this.steps = steps;
    }

    nextStep(step) {
        // console.log(this.steps)
        let currentId = this.steps.indexOf(step)

        if (currentId === (this.steps.length-1)) {
          return;
        } else {
          step.isActive = !step.isActive;
          step.isDone = !step.isDone;
          this.steps[currentId+1].isActive = true;
        }

        this.saveSteps();
    }

    setData(data) {
        this.steps = data
        this.notify(this.steps);
    }

    saveSteps() {
        this.notify(this.steps);
    }

    getCode() {
        return this.steps[0].data.code;
    }

    getId() {
        return this.steps[1].data.id;
    }

    getName() {
        return this.steps[1].data.name;
    }

    getEmail() {
        return this.steps[1].data.email;
    }


    subscribe(listener) {
        this.listener = listener;
    }

    notify(data) {
        // rewrite in local storage
        // if (this.listener) {
        //     this.listener(this.steps);
        // }
        // chrome.storage.sync.get('steps', (res) => {
            // this.steps = res.steps
        // })
        chrome.storage.sync.set({'steps': data})
    }

}

export const stepFactory = {
    create(steps) {
        this.service = new StepService(steps);
        return this.service;
    }
} 