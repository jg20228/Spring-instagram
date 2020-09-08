function commentSend(id){
	if ($("#content").val() === "") {
		alert("댓글 입력이 필요합니다.");
		return;
	}
	let data = $("#frm-"+id).serialize();
	console.log(1, data);
	
	//자바스크립트로 리로드하려고 
	fetch("/comment",{
		method : "post",
		body : data,
		headers : {
			"Content-Type":"application/x-www-form-urlencoded; charset=utf-8"
		}
	}).then(function(res){
		return res.text();
	}).then(function(res){
		alert("댓글 작성 성공");
		location.reload();
	});
	
}


function commentDelete(id){
	
	//자바스크립트로 리로드하려고 
	fetch("/comment/"+id,{
		method : "delete"
	}).then(function(res){
		return res.text();
	}).then(function(res){
		alert("댓글 삭제 성공");
		location.reload();
	});	
}