class StepService {
    constructor(steps) {
        this.steps = steps;
    }

    nextStep(step) {
        let currentId = this.steps.indexOf(step)

        if (currentId === (this.steps.length-1)) {
          return
        } else {
          step.isActive = !step.isActive;
          step.isDone = !step.isDone;
          this.steps[currentId+1].isActive = true;
        }
        this.notify();
    }

    getCode() {
        this.steps[0].data.code;
    }

    getName() {
        this.steps[1].data.name;
    }

    getName() {
        this.steps[1].data.email;
    }

    setStepData(/*data, id*/) {
        
    }

    subscribe(listener) {
        this.listener = listener;
    }

    notify() {
        // rewrite in local storage
        // if (this.listener) {
        //     this.listener(this.steps);
        // }
        // chrome.storage.sync.get('steps', (res) => {
            // this.steps = res.steps
        // })
        chrome.storage.sync.set({'steps': this.steps})
    }

}

export const stepFactory = {
    create(steps) {
        this.service = new StepService(steps);
        return this.service;
    }
} 