const explanation1 = document.getElementById('explanation1');
const explanation2 = document.getElementById('explanation2');

explanation1.addEventListener("click",function(){
    explanation1.innerHTML ="자바를 사용하여 만든 달력에는 투두 리스트를 쓸 기능이 들어가 있습니다.";
    explanation1.style.fontSize = "18px"
});

explanation2.addEventListener("click", function () {
    explanation2.innerHTML = "todo list는 한 눈에 자신의 하루 일과 등을 보여줍니다.";
    explanation2.style.fontSize="18px"
});