
async function follow(id) {
	let response = await fetch("/follows/"+id,{
		method : "post"
	});
	
	let result = await response.text();
	if(result === "ok"){
    	location.reload(true);
	}
}

async function unFollow(id) {
	let response = await fetch("/follows/" + id,{
		method : "delete"
	});
	
	let result = await response.text();
	if(result === "ok"){
    	location.reload(true);
	}
}
