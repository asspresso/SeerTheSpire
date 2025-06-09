package seermod.actions;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

public class 改动次数 extends AbstractGameAction {
    private final int amount;
    private final Mode mode;
    private boolean retrieveCard = false;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotChange = new ArrayList<>();

    public enum Mode {
        INCREASE,
        DECREASE,
        UNLIMITED
    }

    public 改动次数(int amount, Mode mode) {
        this.amount = amount;
        this.mode = mode;
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }

            // First, find cards that cannot be changed
            for (AbstractCard c : this.p.hand.group) {
                int currentExhaustive = ExhaustiveField.ExhaustiveFields.exhaustive.get(c);
                int baseExhaustive = ExhaustiveField.ExhaustiveFields.baseExhaustive.get(c);
                
                // 1. Already unlimited (-1)
                // 2. Mode is UNLIMITED and base PP is <= 2 (too powerful to become unlimited)
                if (currentExhaustive <= -1 || (mode == Mode.UNLIMITED && baseExhaustive <= 2)) {
                    this.cannotChange.add(c);
                }
            }

            // If no cards can be changed, end action
            if (this.cannotChange.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            // If only one card can be changed, process it directly
            if (this.p.hand.group.size() - this.cannotChange.size() == 1) {
                for (AbstractCard c : this.p.hand.group) {
                    if (!this.cannotChange.contains(c)) {
                        processCard(c);
                        this.isDone = true;
                        return;
                    }
                }
            }

            // Remove unchangeable cards from selection
            this.p.hand.group.removeAll(this.cannotChange);

            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open("进行PP调整", 1, false, false, false, false);
                this.tickDuration();
                return;
            }

            if (this.p.hand.group.size() == 1) {
                processCard(this.p.hand.getTopCard());
                this.returnCards();
                this.isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                boolean wasExhausted = processCard(c);
                if (!wasExhausted) {
                    this.p.hand.addToTop(c);
                }
            }
            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.tickDuration();
    }

    private boolean processCard(AbstractCard c) {
        int currentExhaustive = ExhaustiveField.ExhaustiveFields.exhaustive.get(c);
        int baseExhaustive = ExhaustiveField.ExhaustiveFields.baseExhaustive.get(c);
        boolean wasExhausted = false;

        switch (mode) {
            case INCREASE:
                // Increase PP but don't exceed base value
                int newValue = Math.min(currentExhaustive + amount, baseExhaustive);
                ExhaustiveField.ExhaustiveFields.exhaustive.set(c, newValue);
                break;
            case DECREASE:
                // Decrease PP and exhaust if reaches 0
                newValue = currentExhaustive - amount;
                if (newValue <= 0) {
                    this.p.hand.moveToExhaustPile(c);
                    wasExhausted = true;
                } else {
                    ExhaustiveField.ExhaustiveFields.exhaustive.set(c, Math.max(0, newValue));
                }
                break;
            case UNLIMITED:
                // Set to unlimited (-2) and update description
                ExhaustiveField.ExhaustiveFields.exhaustive.set(c, -2);
                // Update the card's description to show "无限" instead of "-1/m"
                String desc = c.rawDescription;
                desc = desc.replace("!stslib:ex! /" + baseExhaustive, "无限");
                c.rawDescription = desc;
                break;
        }
        c.initializeDescription();
        c.superFlash();
        return wasExhausted;
    }

    private void returnCards() {
        // Return unchangeable cards to hand
        for (AbstractCard c : this.cannotChange) {
            this.p.hand.addToTop(c);
        }
        
        // Refresh hand layout
        this.p.hand.refreshHandLayout();
    }
} 