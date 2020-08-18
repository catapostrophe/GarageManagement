package twitter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarketReview {

    @FXML
    private Label result;
    @FXML
    private TextField searchfield;
    @FXML
    private ScrollPane scrollBar;


    /**
     * Code for the GUI button that searches twitter for the given query
     */
    @FXML
    void search() {

        scrollBar.setVvalue(0);
        // API keys to connect to my twitter account
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setDebugEnabled(true)
                .setOAuthConsumerKey("N8SCSue56wY15tmMUccKsLKfc")
                .setOAuthConsumerSecret("ilGJkRlZQTXCTq3gSLxbY7YFj89BU4eVT2J3D6yIfJHdEo69de")
                .setOAuthAccessToken("1283184547882913792-FRIekqt4pPURxPTyfohInAYTND0RvC")
                .setOAuthAccessTokenSecret("sCG8ogAOtv2cPjbh6LHGfp4LX4ImDuWcRHC9JMaao0haA");

        Twitter twitter = new TwitterFactory(builder.build()).getInstance();


        try {
            // Error handling for empty fields
            if (searchfield.getText().isEmpty()) {
                result.setText("Please type in a query before you press the search button");
            } else {

                // Search term
                Query query = new Query(searchfield.getText());
                searchfield.setText("");
                QueryResult results;
                results = twitter.search(query);

                List<Status> tweets = results.getTweets();
                ArrayList<String> tweetlist = new ArrayList<>();

                for (Status tweet : tweets) {
                    // this line prints about a dozen tweets to my terminal for testing purposes
                    //System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());

                    // Add tweets to the tweetlist array
                    tweetlist.add("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());

                }

                // String builder to append items in tweet list array
                StringBuilder format = new StringBuilder();
                for (String spaced : tweetlist) {
                    format.append(spaced);
                    format.append("\n\n");
                }

                // Setting GUI to show tweets
                result.setText(format.toString());
            }

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to look for your query: " + te.getMessage());
        }

    }

    /**
     * Code for the GUI button that deploys current twitter feed
     */
    @FXML
    void feed() {

        // API keys to connect to my twitter account
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setDebugEnabled(true)
                .setOAuthConsumerKey("N8SCSue56wY15tmMUccKsLKfc")
                .setOAuthConsumerSecret("ilGJkRlZQTXCTq3gSLxbY7YFj89BU4eVT2J3D6yIfJHdEo69de")
                .setOAuthAccessToken("1283184547882913792-FRIekqt4pPURxPTyfohInAYTND0RvC")
                .setOAuthAccessTokenSecret("sCG8ogAOtv2cPjbh6LHGfp4LX4ImDuWcRHC9JMaao0haA");
        TwitterFactory factory = new TwitterFactory(builder.build());

        try {

            List<Status> statuses = factory.getInstance().getHomeTimeline();
            ArrayList<String> feed = new ArrayList<>();

            for (Status status : statuses) {
                feed.add("@" + status.getUser().getName() + ": " + status.getText());
            }

            // String builder to append items in feed list array
            StringBuilder format = new StringBuilder();
            for (String spaced : feed) {
                format.append(spaced);
                format.append("\n\n");
            }

            // Setting GUI to show feed
            result.setText(format.toString());

        } catch (TwitterException te) {
            System.out.println("Failed to display your feed: " + te.getMessage());
        }

    }

    /**
     * Changes to the required scene
     * @param event button push event for back
     * @throws IOException
     */
    @FXML
    void back(ActionEvent event) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("systemAdministrator/systemAdministrator.fxml"));
        Scene scene2 = new Scene(view2);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

}
