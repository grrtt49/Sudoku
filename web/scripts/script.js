var selected = false;
var mouseDown = false;
var ctrlDown = false;
var editableSelected = [];
var currentPuzzle;
var currentSolution;

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
        ctrlDown = e.ctrlKey;

        if(selected && !mouseDown) {
            $(".selected").each(function(index, value) {
                if(this.id.includes("editable")) {
                    var char = event.which || event.keyCode || event.key;
                    if(char >= 97 && char <= 105) {
                        char -= 48;
                    }

                    if(char >= 49 && char <= 57) {
                        if(ctrlDown) {
                            e.preventDefault();
                            miniNumber(this, String.fromCharCode(char));
                        }
                        else {
                            $(this).css('font-size', 40);
                            $(this).text(String.fromCharCode(char));
                        }
                    }
                    else if(char == 8 || char == 46) {
                        $(this).text("");
                    }
                }
            });
        }
    });

    $(document).on("keyup", function(){
        ctrlDown = false;
    });

    $(".colorPanel button").mousedown(function(e){
        //TODO: change to class
        e.stopPropagation();
        setColors($(this).find(".colorChoice").css("background-color"));
    });

    $("#dark-theme-toggle").click(function(){
        if($(this).is(":checked")) {
            $("#dark-theme-sheet").attr("href", "styles/dark-theme.css");
        }
        else {
            $("#dark-theme-sheet").attr("href", "styles/light-theme.css");
        }
    });
});

function miniNumber(el, miniNum) {
    console.log($(el).css('font-size'));
    var currentNumber = parseInt($(el).text());

    //if the current number is blank or it's already a small letter block
    if(Number.isNaN(currentNumber) || $(el).css('font-size') == '16px') {
        $(el).css('font-size', 16);
        $(el).text(setOfNumbers($(el).text(), miniNum));
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
    $(".sudokuBoard").show();
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
        //TODO: change it to classes
        $(this).css("background-color", color);
    });
}