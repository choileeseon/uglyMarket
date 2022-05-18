$(function(){
    $('a.deleteConfirm').click(function(){
        if(!confirm("페이지 삭제 하시겠습니까?")) return false;
    })
});
$(function(){
    $('a.deleteProductConfirm').click(function(){
        if(!confirm("상품을 삭제 하시겠습니까?")) return false;
    })
});

//CK에디터 추가
//페이지 내용에 ck에디터
if($('.pageContent').length){ //length는 있어야 나옴 *안쓰면 #content없어도 나오기때문
    ClassicEditor
        .create( document.querySelector( '#content' ))
        .catch( error => {
            console.error( error );
        } );
}

//상품 내용에 ck에디터
if($('#description').length){ //length는 있어야 나옴 *안쓰면 #content없어도 나오기때문
    ClassicEditor
        .create( document.querySelector( '#description' ))
        .catch( error => {
            console.error( error );
        } );
}