export default {
  contextPath: () => {
    return process.env.contextPath;
  },
  getQueryString: name => {
    let reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    let r = window.location.search.substr(1).match(reg);
    if (r !== null) {
      return unescape(r[2]);
    }
    return null;
  },
};