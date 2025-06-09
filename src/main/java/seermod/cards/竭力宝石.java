package seermod.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import seermod.actions.改动次数;
import seermod.character.SEER;
import seermod.util.CardStats;

public class 竭力宝石 extends BaseCard {
    public static final String ID = makeID("Fatigue Gem");
    private static final int BASE_EXHAUSTIVE = 5;

    private static final CardStats info = new CardStats(
            SEER.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.NONE,
            1
    );

    public 竭力宝石() {
        super(ID, info);
        setMagic(5,5);
        ExhaustiveVariable.setBaseValue(this, BASE_EXHAUSTIVE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new 改动次数(magicNumber, 改动次数.Mode.DECREASE));
        addToBot(new DrawCardAction(p, 1));
    }

} 