package seermod.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import seermod.character.SEER;
import seermod.powers.中毒;
import seermod.powers.冻伤;
import seermod.powers.烧伤;
import seermod.util.CardStats;

public class 万里春 extends BaseCard{
    public static final String ID = makeID("Daybreak");
    private static final int BASE_EXHAUSTIVE = 5;

    private static final CardStats info = new CardStats(
            SEER.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            AbstractCard.CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            AbstractCard.CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public 万里春() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(6, 3);
        ExhaustiveVariable.setBaseValue(this, BASE_EXHAUSTIVE);
    }

    private int countIndividualDebuffs(AbstractMonster m) {
        int debuffCount = 0;
        String[] powerIDs = {冻伤.POWER_ID, 烧伤.POWER_ID, 中毒.POWER_ID};

        for (String powerID : powerIDs) {
            if (m.hasPower(powerID)) {
                debuffCount += 1;
            }
        }
        return debuffCount;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        magicNumber = countIndividualDebuffs(m);
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
        addToBot(new DrawCardAction(p, magicNumber));
    }

}