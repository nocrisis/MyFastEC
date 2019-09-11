console.log("test.js")
function setFormValue (jsonStr) {
  var json = JSON.parse(jsonStr)
  $("#name").val(json.name)
  $("#age").val(json.age)
  $("#sex").val(json.sex)
  $("#expire").val(json.expire)
}
function nativeCall () {
    console.log('java调用了nativeCall')
}
function callJS () {
  console.log('java调用了callJS')
  var re = '原生调用js返回"'
  return re
}
$("#test").click(function () {
  var command = { 'action': 'test' }
  if (latte != null) {
    latte.event(JSON.stringify(command))
  } else {
    console.log(java中没有找到latte中介)
  }
})
