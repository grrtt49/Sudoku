var selected = false;
var mouseDown = false;
var ctrlDown = false;
var shiftDown = false;
var changed = 0;
var defaultTab = "#number-tab";
var editableSelected = [];
var currentPuzzle;
var currentSolution;
var startDate;
var timer;


String.prototype.toHHMMSS = function () {
    var sec_num = parseInt(this, 10); // don't forget the second param
    var hours   = Math.floor(sec_num / 3600);
    var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
    var seconds = sec_num - (hours * 3600) - (minutes * 60);

    if (hours   < 10) {hours   = "0"+hours;}
    if (minutes < 10) {minutes = "0"+minutes;}
    if (seconds < 10) {seconds = "0"+seconds;}
    return hours+':'+minutes+':'+seconds;
}

//TODO: Timer

$(document).ready(function() {

    if($("#dark-theme-toggle").is(":checked")) {
        $("#dark-theme-sheet").attr("href", "styles/dark-theme.css");
    }
    else {
        $("#dark-theme-sheet").attr("href", "styles/light-theme.css");
    }

    $(document).on("mouseup", function() {
        mouseDown = false;
    });

    $(document).on("mousedown", function() {
        mouseDown = true;
    });

    $(document).on("mouseenter", ".numberSpace", function() {
        if(selected && mouseDown) {
            $(this).addClass("selected");
        }
    });

    $(document).on("mousedown", ".numberSpace", function(e) {
        e.stopPropagation();
        if(!ctrlDown && selected) {
            $(".numberSpace").removeClass("selected");
            selected = false;
        }

        if(!$(this).hasClass("selected")) {
            $(this).addClass("selected");
            selected = true;
            mouseDown = true;
        }
        else {
            $(this).removeClass("selected");
        }
    });

    $(document).on("dblclick", ".numberSpace", function(e) {
        var num = parseInt($(this).text());
        if(!Number.isNaN(num)) {
            selectAllOfNumber(num);
        }
    });

    $(document).on("mousedown", function() {
        if(!ctrlDown && selected) {
            $(".numberSpace").removeClass("selected");
            selected = false;
        }
    });

    $(document).on("keydown", function(e) {
        checkKeyTabs(e);

        if(selected && !mouseDown) {
            var char = e.which || e.keyCode || e.key;
            $(".selected").each(function(index, value) {
                if(char >= 97 && char <= 105) {
                    char -= 48;
                }

                if(char >= 49 && char <= 57) {
                    changeSquare(char, this, e);
                }
                else if((char == 8 || char == 46) && this.id.includes("editable")) {
                    $(this).text("");
                }
            });
            changed = char;
        }
    });

    $(document).on("keyup", function(e){
        checkKeyTabs(e);
        changed = 0;
    });

    $(".color-container .color-btn").mousedown(function(e){
        e.stopPropagation();
        var colorClass = $(this).find(".colorChoice").attr('class').split(' ')[1];
        setColors(colorClass);
    });

    $("#dark-theme-toggle").click(function(){
        if($(this).is(":checked")) {
            $("#dark-theme-sheet").attr("href", "styles/dark-theme.css");
        }
        else {
            $("#dark-theme-sheet").attr("href", "styles/light-theme.css");
        }
    });

    $(".tab").mousedown(function(e){
        e.stopPropagation();
        defaultTab = "#" + $(this).attr('id');
        selectTab($(this));
    });

    $(".number-btn, .center-number-btn, .corner-number-btn").mousedown(function(e) {
        e.stopPropagation();
        var char = Number.parseInt($(this).text()) + 48;
        $(".selected").each(function() {
            if(this.id.includes("editable")) {
                changeSquare(char, this, e);
            }
        });
    });
});

function changeSquare(char, el, event) {
    key = String.fromCharCode(char);
    if(changed != char) {
        if(ctrlDown && shiftDown) {
            $(el).removeClass("corner-square");
            event.preventDefault();
            setColorFromKey(key);
        }
        else if(ctrlDown) {
            $(el).removeClass("corner-square");
            event.preventDefault();
            miniNumber(el, key);
        }
        else if(shiftDown) {
            event.preventDefault();
            cornerNumber(el, key);
        }
        else {
            if(defaultTab == "#color-tab") {
                event.preventDefault();
                setColorFromKey(key);
            }
            else if(defaultTab == "#center-number-tab") {
                $(el).removeClass("corner-square");
                event.preventDefault();
                miniNumber(el, key);
            }
            else if(defaultTab == "#corner-number-tab") {
                event.preventDefault();
                cornerNumber(el, key);
            }
            else if(el.id.includes("editable")) {
                $(el).removeClass("corner-square");
                $(el).css('font-size', 40);
                $(el).text(key);
            }
        }
    }
}

function cornerNumber(element, cornerNum) {
    if(element.id.includes("editable")) {
        if($(element).css('font-size') != "40px" || $(element).text() == "" || $(element).hasClass("corner-square")) {
            $(element).addClass("corner-square");
            var currentNums = "";
            $(element).children().each(function() {
                currentNums += $(this).text();
            });

            var numberString = setOfNumbers(currentNums, cornerNum); 
            $(element).empty();
            var order = [0, 4, 1, 
                         6, 8, 7, 
                         2, 5, 3];
            for(var i = 0; i < order.length; i++) {
                var index = order[i];
                if(numberString.length <= index) {
                    $(element).append("<span></span>");
                }
                else {
                    $(element).append("<span>" + numberString.charAt(index) + "</span>");
                }
            }
        }
    }
}

function setColorFromKey(num) {
    var colorClass = $("#color-" + num + " span").attr('class').split(" ")[1];
    console.log("changing color: " + colorClass);
    setColors(colorClass);
}

function checkKeyTabs(e) {
    ctrlDown = e.ctrlKey;
    shiftDown = e.shiftKey;
    if(ctrlDown && shiftDown) {
        selectTab($("#color-tab"));
    }
    else if(ctrlDown) {
        selectTab($("#center-number-tab"));
    }
    else if(shiftDown) {
        selectTab($("#corner-number-tab"));
    }
    else {
        selectTab($(defaultTab));
    }
}

function selectTab(el) {
    $(".tab").each(function(index, value) {
        $(this).removeClass("selected-tab");
        var hideClass = $(this).attr("name");
        $("." + hideClass).css("display", "none");
    });

    el.addClass("selected-tab");
    var showClass = el.attr("name");
    $("." + showClass).css("display", "flex");
}

function miniNumber(el, miniNum) {
    if(el.id.includes("editable")) {
        var currentNumber = parseInt($(el).text());

        //if the current number is blank or it's already a small letter block
        if(Number.isNaN(currentNumber) || $(el).css('font-size') == '16px') {
            $(el).css('font-size', 16);
            $(el).text(setOfNumbers($(el).text(), miniNum));
        }
    }
}

function setOfNumbers(str, newNum) {
    var set = [];
    var deleted = false;
    for(var i = 0; i < str.length; i++) {
        var num = parseInt(str.charAt(i));
        if(num != newNum) {
            set.push(num);
        }
        else {
            deleted = true;
        }
    }
    if(!deleted) {
        set.push(newNum);
    }
    set.sort();

    var txt = "";
    for(var i = 0; i < set.length; i++) {
        txt += set[i];
    }
    return txt;
}

function getPuzzle(){
    $('.loadingSymbol').show(); 
    $.get("/create_puzzle", function(r){
        $('.loadingSymbol').hide(); 
        var puzzle = JSON.parse(r);
        createSudoku(puzzle['puzzle'], puzzle['solution']);
        startDate = new Date;
        timer = setInterval(updateTimer, 1000);
    });
}

function createSudoku(board, solution) {
    $(".sudokuBoard").empty();
    currentPuzzle = board;
    currentSolution = solution;
    for(var y = 0; y < board.length; y++) {
        for(var x = 0; x < board.length; x++) {
            if(board[y][x] != 0) {
                $(".sudokuBoard").append("<div class='numberSpace'>" + board[y][x] + "</div>");
            }
            else {
                $(".sudokuBoard").append("<div id='editable" + x + "_" + y + "' class='numberSpace editable'></div>"); //<input type='text' class='sudokuInput'>
            }

            if(x % 3 == 2 && x != board.length - 1) {
                $(".sudokuBoard").append("<div class='boxGap'></div>");
            }
        }

        if(y % 3 == 2 && y != board.length - 1) {
            for(var i = 0; i < 11; i++) {
                $(".sudokuBoard").append("<div class='rowGap'></div>");
            }
        }
    }
    $(".sudokuBoard").css('visibility', 'visible');
}

function getBoard() {
    var board = [];
    var y = -1;
    var x = 0;
    $(".numberSpace").each(function(index, value){
        if(index % 9 == 0) {
            board.push([]);
            y++;
            x = 0;
        }
        var number = parseInt($(this).text());
        if(!Number.isNaN(number)) {
            board[y][x] = number;
        }
        else {
            board[y][x] = 0;
        }
        x++;
    });
    return board;
}

function checkSudoku(board, solution) {
    for(var y = 0; y < board.length; y++) {
        for(var x = 0; x < board.length; x++) {
            if(board[y][x] != solution[y][x]) {
                return false;
            }
        }
    }
    return true;
}

function checkCurrentSudoku() {
    return checkSudoku(getBoard(), currentSolution);
}

function selectAllOfNumber(n) {
    $(".numberSpace").each(function(index, value){
        var number = parseInt($(this).text());
        if(number == n) {
           $(this).addClass("selected");
            selected = true;
        }
    });
}

function setColors(color) {
    $(".selected").each(function(){
        var sameClassRemoved = false;
        var classes = $(this).attr('class').split(' ');
        for(var i = 0; i < classes.length; i++) {
            if(classes[i].includes("bg-")) {
                if(classes[i] == color) {
                    sameClassRemoved = true;
                }
                $(this).removeClass(classes[i]);
            }
        }

        if(!sameClassRemoved) {
            $(this).addClass(color);
        }
    });
}

function selectIncorrect() {
    var y = -1;
    var x = 0;
    $(".numberSpace").each(function(index, value){
        if(index % 9 == 0) {
            y++;
            x = 0;
        }
        var number = parseInt($(this).text());

        if(!Number.isNaN(number) && number != currentSolution[y][x]) {
            $(this).addClass("selected");
        }

        x++;
    });
}

function updateTimer() {
    $("#timer").text(Math.round((new Date() - startDate) / 1000).toString().toHHMMSS());
}