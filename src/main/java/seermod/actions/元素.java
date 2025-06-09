package seermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

import seermod.cards.毒粉;
import seermod.cards.漩涡;
import seermod.cards.火花;

public class 元素 extends AbstractGameAction {
    private final int numberOfCards;
    private final ArrayList<AbstractCard> cardPool;
    private final boolean upgraded;

    public 元素(int numberOfCards, boolean upgraded) {
        this.numberOfCards = numberOfCards;
        this.upgraded = upgraded;
        this.actionType = ActionType.CARD_MANIPULATION;

        this.cardPool = new ArrayList<>();
        this.cardPool.add(new 毒粉());
        this.cardPool.add(new 漩涡());
        this.cardPool.add(new 火花());
    }

    public 元素(int numberOfCards) {
        this(numberOfCards, false);
    }

    @Override
    public void update() {
        if (this.numberOfCards > 0) {
            for (int i = 0; i < this.numberOfCards; i++) {
                int randomIndex = AbstractDungeon.cardRandomRng.random(this.cardPool.size() - 1);

                AbstractCard cardToAdd = this.cardPool.get(randomIndex).makeCopy();

                if (this.upgraded && cardToAdd.canUpgrade()) {
                    cardToAdd.upgrade();
                }

                this.addToBot(new MakeTempCardInHandAction(cardToAdd, 1));
            }
        }

        this.isDone = true;
    }
}