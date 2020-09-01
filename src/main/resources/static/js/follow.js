
function follow(id) {
	let url = "/follows/"+id;
	fetch(url,{
		method: "POST"
    }).then(function(res){
    	console.log(res);
    	location.reload(true);
		return res.text();							
	});
}

function unfollow(id) {
	let url = "/follows/"+id;
	fetch(url,{
		method: "DELETE"
    }).then(function(res){
    	console.log(res);
    	location.reload(true);
		return res.text();							
	});
}