function verMais() {
    var el = document.getElementById("bver");
   if (el.firstChild.data == "Ver +") {
    $(".galeria2").slideDown("slow");
       el.firstChild.data = "Ver -";
   }
   else {
    $(".galeria2").slideUp("slow");
     el.firstChild.data = "Ver +";
   }
  }