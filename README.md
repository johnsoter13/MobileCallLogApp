This is an app for viewing your phone logs!

Questions
1. What are the libraries or frameworks you have chosen? and why?

I didn't use many libraries for this project. I used the SimpleDateFormat library in order to take the date, which comes in milliseconds
since the epoch, and convert it into something readable by humans. I used the PhoneNumberUtils to format phone numbers in a simple way
into a more readable output.

2. If you had more time, what would you have done better?

If I had more time I would've implemented the auto refresh feature so that you don't have to pull down to refresh the list of calls.
This could be done in a service. I also could've added more functionality like adding contact names instead of just numbers.

3. What are some of the key architecture considerations?

I thought a lot about what the best way to retrieve the phone logs from the user and how to display them on the phone. I eventually came
to the conclusion that to properly implement OOP I should create a PhoneCall class to store the information in and a PhoneCallAdapter to
handle this custom class.

The overall flow of the app is pretty simple as the only action the user has is to accept/deny permissions and refresh the list. So I
designed the app so that if the user denies permission but later decides that they want to accept permission, they can pull down to refresh
the list which will prompt them with another chance to accept/deny permission. Refreshing the list while permissions are currently denied
implies that the user wants to see their call log so it wouldn't be annoying to the user to ask for permission again.
