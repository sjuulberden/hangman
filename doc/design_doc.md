# Design Document Hangman - the Evil version

General Description:
In the game of hangman the computer plays against a user. The user will try to guess the word chosen by the
computer. For guessing the user gets seven chances, if he didn't guess the word at the end of the game he will be hung! 
However in the game the computer can be evil! This means that he can change the word, without the user noticing this! Are you able to beat the evil computer?

### Begin Screen

On the begin screen of the app, the user will see a picture with the logo of the app. Also the user will see 3 buttons:

1. Start Game
2. Settings 
3. High Scores

Start Game will start the Hangman Game in evil modes. In Evil mode the computer constantly tries to dodge the user�s guesses by changing the word it�s thinking of. 

The choice for these three buttons is made because of its simplicity, the user starts up the app and can start a game, change some settings or view the highscores. The choice not to start a game by opening is made because that annoys the user if the user wants to change preferences at the start or view his/her best scores. 

Another reason to first let the user change settings first is because of the formula which calculates the highscores. This formula uses the number of guesses, the mode (evil vs normal) and the length of the word. As the users wants to optimise his score he proably also wants to optimise these settings before starting the game.

### Game View

Button 1 "Start Game" brings the user to the game view screen. 

Our app does not have a normal keyboard but has a 26 button screen where the user can push a button wich then is shown pushed so its clear which buttons has been pusched and which are still availeble.

Their will be a stripe where the letters are being showed until the word has been geuessed by the user. On top of the screen is a hangman that each time that the user guesses someting wrong is going to be more hung, until the user has used all his/her guesses, or has won. We choose to implement 26 buttons so the user can't provide any non-anphabatical or multiple letter input.

### Settings

Button 2 brings the user to the settings screen where there are three options, option 1 is to choose between evil and normal hangman. Option 2 is the ammount of guesses that the user has, either 4,6 or 8. And option three is the wordlength witch can be either 4,6,8 or 10. The choise to hardcode these is because it provides a solid base to form score furmula that is faire and also makes sure that no corner cases emerce. These option also provide a good range of options, from very easy to very hard. 

### High Scores

Button 3 bring the user to the high score page where 10 highest scores, including the names of the once who scores them are displayed from high to low.

### Technical description

The game consists of three classes. Firstly there is GameActivity witch takes care of the general game activities like setting the gallow and choosing a (randow) word. It also checks weather the user has won or lost. Secondly there is LetterAdapter witch takes care of setting the letter buttons. Lastly there is MainActivity. MainActivity determines what happens when the app is openend or what to do when something is clicked.

As for showning the gallow, it is choosen to cut the picture in 7 parts. This means that when the first wrong guesses is made 1/7th of hangman is shown. When the user lost and there are no more guesses left the whole hangman is shown. As for showing the letter buttons it is chosen to create a function LetterPressed() which changes the aesthetics so the user is aware of witch buttons he pressed.  

For the layout there are serveral layout files. The layout match the class files, this means that there are: activity_main.xml, activity_game.xml and letter.xml. Here main determines the layout of general game, game determines the layout of the game and letter determines the layout of the letters. 

There are two lists used in the hangman app. Firstly there is the wordlist witch contains strings of words, this file is called words.xml, located in the values folder. Secondly there is the highscorelist, also located in the values folder. In this values folder there is a second file, the string.xml file. This file contains the hardcoded strings.

### Advanced sketches of UIs

See the pictures in the doc directory.

### Acknowledgement

This app is developed and copyrighted by Thijs Verheul (10003265) and Sjuul Berden (10694498).
