let circles = document.querySelectorAll(".lesson-part");
circles.forEach((el) => {
    el.addEventListener("click", (e) => {
        let id = e.target.dataset.id;
        circles.forEach((el2) => {
            if (el2.dataset.id <= id) {
                el2.classList.add("active");
            } else {
                el2.classList.remove("active");
            }
        });
    });
});

let lessons = document.querySelector(".lessons");
let lessonsHeader = lessons.querySelector(".lesson-parts");
let lessonBody = lessons.querySelector(".lesson-body");
let lessonHeaderNodes = lessons.querySelectorAll(".lesson-parts > div");
let lessonBodyNodes = lessons.querySelectorAll(".lesson-body > div");

for (let i = 0; i < lessonHeaderNodes.length; i++) {
    lessonHeaderNodes[i].addEventListener("click", function () {
        lessonHeaderNodes[i].classList.add("active");
        lessonBody.querySelector(".active").classList.remove("active");
        lessonBodyNodes[i].classList.add("active");
    });
}

var slideIndex = 1;
showDivs(slideIndex);

function plusDivs(n) {
    showDivs((slideIndex += n));
}

function showDivs(n) {
    var i;
    var x = document.getElementsByClassName("mySlides");
    if (n > x.length) {
        slideIndex = 1;
    }
    if (n < 1) {
        slideIndex = x.length;
    }
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    x[slideIndex - 1].style.display = "block";
}

var slideIndex = 1;
showDivsMinor(slideIndex);

function plusDivsMinor(n) {
    showDivsMinor((slideIndex += n));
}

function showDivsMinor(n) {
    var i;
    var x = document.getElementsByClassName("minorSlides");
    if (n > x.length) {
        slideIndex = 1;
    }
    if (n < 1) {
        slideIndex = x.length;
    }
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    x[slideIndex - 1].style.display = "block";
}
