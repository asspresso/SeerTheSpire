package seermod.cards;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import seermod.character.SEER;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import seermod.powers.禁赛;
import seermod.util.CardStats;

public class 掉线 extends BaseCard{
    public static final String ID = makeID("Offline");

    private static final CardStats info = new CardStats(
            SEER.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            AbstractCard.CardType.CURSE, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            AbstractCard.CardRarity.CURSE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            AbstractCard.CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public 掉线() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void triggerWhenDrawn() {
        addToBot(new LoseEnergyAction(1));
        addToBot(new ApplyPowerAction(player, player, new 禁赛(player, player, 1), 1));
    }


}
