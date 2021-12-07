import { expect } from 'chai'
import { createLocalVue, mount, shallowMount } from '@vue/test-utils'
import Login from '@/components/Login.vue'
//import router from '@/router'
import Vue from 'vue'
//import VueRouter from 'vue-router'
import Vuetify from 'vuetify'
global.requestAnimationFrame = function () {}
global.cancelAnimationFrame = function () {}
global.localStorage = window.localStorage;

Vue.use(Vuetify)
//Vue.use(router)

describe('Login.vue', () => {
  // First Test Case
  const localVue = createLocalVue()
  let vuetify
  //let vuerouter

  beforeEach(() => {
    vuetify = new Vuetify()
    //vuerouter = new VueRouter()
  })

  it ('login should render correctly', async () => {
    let wrapper = mount(Login, {
      localVue,
      vuetify,
      //vuerouter,
    });
    //model should be false i.e dialog hidden
    expect(wrapper.vm.dialog).to.be.false;
    //click on login button
    //wrapper.find({ ref: 'loginDialogButton' }).trigger('click');
    const loginDialogButton = wrapper.findComponent({ ref: 'loginDialogButton' })
    expect(loginDialogButton.exists()).to.equal(true);
    //click on login dialog button
    await loginDialogButton.trigger('click');
    //wait till the click is captured
    await wrapper.vm.$nextTick();
    //model should be true i.e dialog showing
    expect(wrapper.vm.dialog).to.be.true;
    //check if login dialog is visible
    const loginDialog = wrapper.findComponent({ ref: 'loginDialog' })
    expect(loginDialog.exists()).to.equal(true);
    expect(loginDialog.isVisible()).to.equal(true);
    //check login and cancel button are visible
    const buttonCloseLoginDialog = wrapper.findComponent({ ref: 'buttonCloseLoginDialog' })
    expect(buttonCloseLoginDialog.exists()).to.equal(true);
    expect(buttonCloseLoginDialog.isVisible()).to.equal(true);
    const buttonLogintoApp = wrapper.findComponent({ ref: 'buttonLogintoApp' })
    expect(buttonLogintoApp.exists()).to.equal(true);
    expect(buttonLogintoApp.isVisible()).to.equal(true);
    //click on cancel button
    await buttonCloseLoginDialog.trigger('click');
    //wait till the click is captured
    await wrapper.vm.$nextTick();
    // confirm model is now false
    expect(wrapper.vm.dialog).to.be.false;
    //check if login dialog is now NOT visible
    //// expect(loginDialog.isVisible()).to.equal(false);
    //check login and cancel button are now NOT visible
    expect(buttonCloseLoginDialog.isVisible()).to.equal(false);
    expect(buttonLogintoApp.isVisible()).to.equal(false);
    //confirm register button exists and is visible
    const registerButton = wrapper.findComponent({ ref: 'registerButton' })
    expect(registerButton.exists()).to.equal(true);
    expect(registerButton.isVisible()).to.equal(true);
  })
});
