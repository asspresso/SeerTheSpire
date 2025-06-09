package seermod.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import seermod.actions.改动次数;
import seermod.character.SEER;
import seermod.util.CardStats;

public class 活力药剂 extends BaseCard {
    public static final String ID = makeID("Vitality Potion");
    private static final int BASE_EXHAUSTIVE = -2;

    private static final CardStats info = new CardStats(
            SEER.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.NONE,
            1
    );

    public 活力药剂() {
        super(ID, info);
        setMagic(2,1);
        setBlock(7, 2);
        ExhaustiveVariable.setBaseValue(this, BASE_EXHAUSTIVE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new 改动次数(magicNumber, 改动次数.Mode.INCREASE));
        addToBot(new GainBlockAction(p, p, block));
    }

} 