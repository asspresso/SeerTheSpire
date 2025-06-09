package seermod.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import seermod.character.SEER;
import seermod.powers.中毒;
import seermod.powers.冻伤;
import seermod.powers.烧伤;
import seermod.util.CardStats;

public class 奇幻光芒 extends BaseCard{
    public static final String ID = makeID("Dramatic Light");
    private static final int BASE_EXHAUSTIVE = 1;

    private static final CardStats info = new CardStats(
            SEER.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            AbstractCard.CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            AbstractCard.CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public 奇幻光芒() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        ExhaustiveVariable.setBaseValue(this, BASE_EXHAUSTIVE);
    }

    private void checkOutDebuff(AbstractMonster monster) {
        String[] powerIDs = {冻伤.POWER_ID, 烧伤.POWER_ID, 中毒.POWER_ID};
        for (String powerID : powerIDs) {
            if (monster.hasPower(powerID)) {
                AbstractPower power = monster.getPower(powerID);
                int powerAmount = power.amount;

                for (int i = 0; i < powerAmount; i++) {
                    power.atStartOfTurn();
                    power.atEndOfRound();
                }
            }
        }
    }

    private void checkOutDebuffs() {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            checkOutDebuff(monster);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            checkOutDebuff(m);
        } else {
            checkOutDebuffs();
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            target = CardTarget.ALL_ENEMY;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
