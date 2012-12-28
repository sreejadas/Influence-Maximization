
/***************************************
 * This reformates the tweets
 * eliminates multiple charactes to one word greaaaaaaat (TO BE IMPLEMENTED)
 * eliminates references @MaryGer
 * eliminates urls inside tweet
 * ask Matthjis for smileys
 ***************************************/


import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author maryger
 */
public class TextNormaliser
{

    static String tweet = "";

    public TextNormaliser(String tweet)
    {

        this.tweet = tweet;

    }

    public String getTweet()
    {
        return tweet;
    }

    /**
     * Get tweet normalized without noise
     * @param Tweet
     * @return 
     */
	 /*replaces url by the word URL
	 *@user with AT_USER
	 *#word with word
	 */
    public static String getTweetWithoutUrlsAnnotations(String Tweet)
    {
		Tweet =Tweet.replaceAll("((http://)|(www[.]))[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]", " ");
		Tweet=Tweet.replaceAll("@[^\\s]+"," ");
		Tweet=Tweet.replaceAll("#([^\\s]+)","$1");
		return Tweet;

    }

    /**
     * Try to detect a smiley at the given tweet and it returns the appropriate score
     * add this score to the general score as well
     * ask MIhai for tokenizer
     * @return 
     */
    public static double detectSmiley(String tweet, HashMap<String, Double> smileys)
    {

        double score = 0;

        StringTokenizer toks = new StringTokenizer(tweet, " ");
        while (toks.hasMoreTokens())
        {
            String token = toks.nextToken();
            if (smileys.containsKey(token))
            {
                score = smileys.get(token);
                return score;

            }

        }

        return  score;
    }

    public static String removeDuplicates(String text)
    {

        char[] str = text.toCharArray();
		System.out.println(str);
        if (str == null)
        {
            return null;
        }
        int len = str.length;
        if (len < 2)
        {
            return text;
        }
        int tail = 1;
        for (int i = 1; i < len; ++i)
        {
            int j;
            for (j = 0; j < tail; ++j)
            {
                if (str[i] == str[j])
                {
                    break;
                }
            }
            if (j == tail)
            {
                str[tail] = str[i];
                ++tail;
            }
        }
        str[tail] = 0;
		return (new String(str));
    }

    public static String toLowerCase(String tw)
    {


        return tw.toLowerCase();

    }

   /* public static void main(String[] args)
    {
        System.out.println("@smarrison i would've been the first, but i didn't have a gun.    not really though, zac snyder's just a doucheclown.");
        System.out.println(getTweetWithoutUrlsAnnotations("@smarrison i would've been the first, but i didn't have a gun.    not really though, zac snyder's just a doucheclown."));

    }*/
}